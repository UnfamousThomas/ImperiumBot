package guild.imperium.commands.mod.punish;

import guild.imperium.ImperiumBot;
import guild.imperium.commands.api.BotSettings;
import guild.imperium.commands.api.DiscordCommand;
import guild.imperium.object.PunishPointObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PunishLogCommand extends DiscordCommand {
	public PunishLogCommand(String rank) {
		super(rank, "punishlog");
		minArgs = 1;
		maxArgs = 2;
		//1Command (2 USER)
		description = "View your punish history.";
	}
	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent e) {
		switch (args.size()) {
			case 0:
				sendPunishments(e, m);
				break;
			case 1:
				if(e.getMessage().getMentionedMembers().size() == 1) {
					Member member = e.getMessage().getMentionedMembers().get(0);
					sendPunishments(e, member);
				} else if(BotSettings.g.getMemberById(Long.parseLong(args.get(1))) != null) {
					Member member = BotSettings.g.getMemberById(Long.parseLong(args.get(1)));
					sendPunishments(e, member);
				}
		}
	}

	public MessageEmbed Punishments(Long userID, PunishPointObject object) {
			EmbedBuilder embedbuilder = new EmbedBuilder();
			embedbuilder.setTitle("Punishment Logs (" + BotSettings.g.getMemberById(userID).getEffectiveName() + ") - " + object.getUUID());
			embedbuilder.setColor(Color.decode("#3498db"));
			embedbuilder.addField("Logs:", "Moderator: " + BotSettings.g.getMemberById(object.getMod()).getEffectiveName() + "\nReason: " + object.getReason() + "\nPoints: " + object.getPoints() + "\nExecuted at: " + object.getExecuted() + "\nExpires/Expired at: " + object.getExpired() + "\n", false);
			embedbuilder.setFooter(BotSettings.g.getName(), BotSettings.g.getIconUrl());

			return embedbuilder.build();
		}

		public void sendPunishments(MessageReceivedEvent e, Member m) {
			List<MessageEmbed> embeds = new ArrayList<>();
			ImperiumBot.getInstance().getManager().getUser(m.getIdLong()).getPunishments().forEach(punishPointObject -> {
				MessageEmbed embed = Punishments(m.getIdLong(), punishPointObject);
				e.getChannel().sendMessage(embed).queue();
				embeds.add(embed);
			});
			if(embeds.isEmpty()) {
				EmbedBuilder embedbuilder = new EmbedBuilder();
				embedbuilder.setTitle("Punishment Logs (" + BotSettings.g.getMemberById(m.getUser().getIdLong()).getEffectiveName() + ")");
				embedbuilder.setColor(Color.decode("#3498db"));
				embedbuilder.addField("Logs", "No logs found. Sorry!", false);
				embedbuilder.setFooter(BotSettings.g.getName(), BotSettings.g.getIconUrl());
				e.getChannel().sendMessage(embedbuilder.build()).queue();
			}
		}
	}


