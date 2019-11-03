package guild.imperium.commands.dev;

import guild.imperium.commands.api.DiscordCommand;
import guild.imperium.utils.redis.payloads.ServerCreatePayload;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class SecretDevCommand extends DiscordCommand {
	public SecretDevCommand(String rank) {
		super(rank, "secretdev");
		description = "oh no you dont!";
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent e) {
		new ServerCreatePayload("lobby1", false, true, "localhost:5000/release-lobby").publish();
		e.getChannel().sendMessage("secret done.").queue();
	}
}
