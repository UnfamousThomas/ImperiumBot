package guild.imperium.commands.mod.punish;

import guild.imperium.ImperiumBot;
import guild.imperium.commands.api.BotSettings;
import guild.imperium.commands.api.DiscordCommand;
import guild.imperium.object.PunishPointObject;
import guild.imperium.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.RandomStringUtils;

import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PunishCommand extends DiscordCommand {
	public PunishCommand(String rank) {
		super(rank, "punish");
		minArgs = 3;
		//1COMMAND 2MEMBER 3POINTS 4+REASON
		description = "Punish someone - Args: member tag/id points reason";
	}
	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent e) {
		if(e.getMessage().getMentionedMembers().size() == 1 && args.size() >= 4) {
			Member member = e.getMessage().getMentionedMembers().get(0);
			Integer points = Integer.valueOf(args.get(2));
			args.remove(0);
			args.remove(0);
			args.remove(0);
			String reason = String.join(" ", args);
			Punish(m, member, points, reason, e);

		} else if(BotSettings.g.getMemberById(Long.parseLong(args.get(1))) != null) {
			Member member = BotSettings.g.getMemberById(Long.parseLong(args.get(1)));
			Integer points = Integer.valueOf(args.get(2));
			args.remove(0);
			args.remove(0);
			args.remove(0);
			String reason = String.join(" ", args);
			Punish(m, member, points, reason, e);
			//	public MessageEmbed log(int amount, String reason, Member moderator, Member punished, String uuid) {
		}
		e.getMessage().delete().queue();
	}

	private void Punish(Member punisher, Member punished, Integer points, String reason, MessageReceivedEvent e) {
		String UUID = RandomStringUtils.random(10, true, true);
		MySQLManager.select("SELECT * FROM punishment_points WHERE UUID =?", resultSet -> {
			if(!resultSet.next()) {
				MySQLManager.execute("INSERT INTO punishment_points (UUID, userid, executor, amount, assigned_at, expires_at, reason) VALUES (?,?,?, ?,NOW(), NOW() + INTERVAL 60 DAY, ?)",
						UUID,
						punished.getUser().getIdLong(),
						punisher.getUser().getIdLong(),
						points,
						reason);

				punisher.getUser().openPrivateChannel().queue(channel -> {
					channel.sendMessage("User got " + points + " points added to their punishment record, the reason was " + reason + ". The unique identifier of this is: " + UUID).queue();
				});
				punished.getUser().openPrivateChannel().queue(channel -> {
					channel.sendMessage("You got " + points + " points added to your punishment record, the reason is " + reason + ". If you wish to appeal provide the unique identifier. The unique identifier is: " + UUID).queue();
				});
				e.getGuild().getTextChannelById(BotSettings.MODLOG).sendMessage(log(points, reason, punisher, punished, UUID)).queue();
				ImperiumBot.getInstance().getManager().getUser(punished.getIdLong()).getPunishments().add(new PunishPointObject(punished.getIdLong(), punisher.getIdLong(), reason, points, UUID,new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis() + 5184000000L)));
			} else {
				Punish(punisher, punished, points, reason, e);

			}
		}, UUID);
	}

	public MessageEmbed log(int amount, String reason, Member moderator, Member punished, String uuid) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		Date end = new Date(System.currentTimeMillis() + 5256000000L);
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("LOG - PUNISH");
		builder.setColor(Color.decode("#f1c40f"));
		builder.setThumbnail(moderator.getUser().getEffectiveAvatarUrl());
		builder.addField("Punished", punished.getEffectiveName(), false);
		builder.addField("Punisher", moderator.getEffectiveName(), false);
		builder.addField("Reason", reason, false);
		builder.addField("Points", String.valueOf(amount), false);
		builder.addField("Current Date", sdf.format(date), true);
		builder.addField("Current Time", stf.format(date), true);
		builder.addField("End date", sdf.format(end), true);
		builder.addField("End Time",stf.format(end), true);
		builder.addField("UUID", uuid, false);
		return builder.build();
}}
