package guild.imperium.commands.admin;

import guild.imperium.commands.api.BotSettings;
import guild.imperium.commands.api.DiscordCommand;
import guild.imperium.commands.api.RoleHierarchy;
import guild.imperium.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NewsCommand extends DiscordCommand {
	public NewsCommand(String rank) {
		super(rank, "news");
		minArgs = 2;
		description = "publish & subscribe! | Lets the higher ups publish news and others to subscribe to the news.";
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent e) {
			switch (args.get(1)) {
				case "publish":
					if(RoleHierarchy.isHigherorEqualsRole(e.getMember().getRoles().get(0),BotSettings.g.getRolesByName("Duke - Captain", true).get(0))) {
						args.remove(0);
						args.remove(0);
						MessageEmbed embed = announceMaker(String.join(" ", args), m);
						MySQLManager.select("SELECT * FROM news_list", resultSet -> {
							while (resultSet.next()) {
								long memberid = resultSet.getLong("member_id");
								if (BotSettings.g.getMemberById(memberid) != null) {
									Member member = BotSettings.g.getMemberById(resultSet.getLong("member_id"));
									member.getUser().openPrivateChannel().queue(channel -> {
										channel.sendMessage(embed).queue();
									});
								}
							}
						});
						BotSettings.g.getTextChannelById(BotSettings.NEWSLOG).sendMessage(embed).queue();
					} else {
						e.getChannel().sendMessage("No.").queue(message -> message.delete().queueAfter(1, TimeUnit.MINUTES));
					}
					break;

				case "subscribe":
					MySQLManager.select("SELECT * FROM news_list WHERE member_id=?", resultSet -> {
						if(resultSet.next()) {
							MySQLManager.execute("DELETE FROM news_list WHERE member_id=?", e.getAuthor().getIdLong());
							m.getUser().openPrivateChannel().queue(channel -> {
								channel.sendMessage("Unsubscribed from news list.").queue();
							});
						} else {
							MySQLManager.execute("INSERT INTO news_list (member_id) VALUES (?)", e.getAuthor().getIdLong());
							m.getUser().openPrivateChannel().queue(channel -> {
								channel.sendMessage("Subscribed to the news list").queue();
							});
						}
					}, e.getAuthor().getIdLong());
					break;
			}
		}
	//TODO: CONVERT PUBLISH & SUBSCRIVE TO SUBCOMMANDS

	private MessageEmbed announceMaker(String news, Member m) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Announcement");
		builder.setColor(Color.decode("#3498db"));
		builder.addField("Content", news, false);
		builder.addField("Date", sdf.format(date), false);
		builder.addField("Time", stf.format(date), false);
		builder.setFooter(m.getEffectiveName(), m.getUser().getEffectiveAvatarUrl());
		return builder.build();
	}
}
