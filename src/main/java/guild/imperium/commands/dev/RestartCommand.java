package guild.imperium.commands.dev;

import guild.imperium.commands.api.DiscordCommand;
import guild.imperium.utils.redis.payloads.ImperiumRestartPayload;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class RestartCommand extends DiscordCommand {
	public RestartCommand(String rank) {
		super(rank, "restart");
	}
	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent e) {
		new ImperiumRestartPayload().publish();
	}
}
