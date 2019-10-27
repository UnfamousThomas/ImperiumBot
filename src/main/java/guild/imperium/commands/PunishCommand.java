package guild.imperium.commands;

import guild.imperium.commands.api.DiscordCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class PunishCommand extends DiscordCommand {
	public PunishCommand(String rank) {
		super(rank, "punish");
		minArgs = 3;
		description = "Punish someone!";
	}
	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent e) {

	}
}
