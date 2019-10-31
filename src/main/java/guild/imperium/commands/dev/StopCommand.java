package guild.imperium.commands.dev;

import guild.imperium.commands.api.BotSettings;
import guild.imperium.commands.api.DiscordCommand;
import guild.imperium.utils.redis.payloads.ImperiumRestartPayload;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class StopCommand extends DiscordCommand {
	public StopCommand(String rank) {
		super(rank, "stop");
		description = "Stops the bot.";
	}
	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent e) {
		BotSettings.g.getTextChannelById(BotSettings.SECRETBOTCOMMANDS).sendMessage("Going offline!").queue();
		e.getJDA().shutdownNow();
	}
}
