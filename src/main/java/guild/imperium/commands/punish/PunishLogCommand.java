package guild.imperium.commands.punish;

import guild.imperium.commands.api.BotSettings;
import guild.imperium.commands.api.DiscordCommand;
import guild.imperium.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		e.getChannel().sendMessage(embedder(e.getAuthor().getIdLong(), e)).queue();
	}

	private MessageEmbed embedder(Long id, MessageReceivedEvent e) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Punishment");
		builder.setColor(Color.decode("#c2f949"));
		builder.addField("Logs:\n", getPoints(id), false);
		builder.setFooter(e.getGuild().getName(), e.getGuild().getIconUrl());
		return builder.build();	}

		//TODO: FIGURE OUT WHY THE FUCK LOGS ARE EMPTY

	private String getPoints(long id) {
		StringBuilder builder = new StringBuilder();
		MySQLManager.select("SELECT * FROM punishment_points WHERE userid=?", resultSet -> {
				while (resultSet.next()) {
					String newline = "UUID: " + resultSet.getString("UUID") + "\nPoints:" + resultSet.getInt("amount") + "\nPunished by: " + BotSettings.g.getMemberById(resultSet.getLong("executor")).getEffectiveName() + "\nReason:" + resultSet.getString("reason");
					builder.append(newline);
				}

				if(builder.length() < 1) {
					builder.append("No logs found");
				}

				}, id);
		return builder.toString();
	}
}
