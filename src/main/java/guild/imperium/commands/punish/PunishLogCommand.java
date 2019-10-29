package guild.imperium.commands.punish;

import guild.imperium.ImperiumBot;
import guild.imperium.commands.api.BotSettings;
import guild.imperium.commands.api.DiscordCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
		Punishments(m.getIdLong()).forEach(embed -> {
			e.getChannel().sendMessage(embed).queue();
		});
	}

	public List<MessageEmbed> Punishments(Long userID) {
		List<MessageEmbed> embeds = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		ImperiumBot.getInstance().getManager().getUser(userID).getPunishments().forEach(punishment -> {
			builder.append("Moderator: ").append(BotSettings.g.getMemberById(punishment.getMod()).getEffectiveName()).append("\nReason: ").append(punishment.getReason()).append("\nPoints: ").append(punishment.getPoints()).append("\nExecuted at: ").append(punishment.getExecuted()).append("\nExpires/Expired at: ").append(punishment.getExpired()).append("\nUUID: ").append(punishment.getUUID()).append("\n");
			EmbedBuilder embedbuilder = new EmbedBuilder();
			embedbuilder.setTitle("Punishment Logs - " + BotSettings.g.getMemberById(punishment.getUserid()).getEffectiveName());
			embedbuilder.setColor(Color.decode("#3498db"));
			embedbuilder.addField("Logs:", builder.toString(), false);
			embedbuilder.setFooter(BotSettings.g.getName(), BotSettings.g.getIconUrl());
			embeds.add(embedbuilder.build());
		});
		return embeds;
	}


}
