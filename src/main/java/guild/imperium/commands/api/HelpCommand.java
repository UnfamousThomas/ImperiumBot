package guild.imperium.commands.api;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HelpCommand extends DiscordCommand {
	public HelpCommand(String rank) {
		super(rank, "help");
		minArgs = 0;
		maxArgs = 2;
		description = "Shows you commands";
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent e) {
		switch (args.size()) {
			case 1:
				e.getMember().getUser().openPrivateChannel().queue(channel -> {
					channel.sendMessage(embedAll(e)).queue();
				});
				e.getMessage().delete().queue();
				break;
			case 2:
				e.getMember().getUser().openPrivateChannel().queue(channel -> {
					channel.sendMessage(embedCommand(e, args.get(1))).queue();
				});
				e.getMessage().delete().queue();
				break;
		}
	}

	private MessageEmbed embedCommand(MessageReceivedEvent e, String name) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Help - " + name);
		builder.setColor(Color.decode("#c2f949"));
		builder.addField("Command:\n", ReturnCommandHelp(name), false);
		builder.setFooter(e.getGuild().getName(), e.getGuild().getIconUrl());
		return builder.build();
	}
	private MessageEmbed embedAll(MessageReceivedEvent e) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Help");
		builder.setColor(Color.decode("#c2f949"));
		builder.addField("Commands:\n", ReturnAllCommands(), false);
		builder.setFooter(e.getGuild().getName(), e.getGuild().getIconUrl());
		return builder.build();
	}

	private String ReturnAllCommands() {
	return DiscordCommandManager.getInstance().getHelpManager().getHelp();
	}

	private String ReturnCommandHelp(String command) {
		return DiscordCommandManager.getInstance().getHelpManager().getCommandHelp(command);
	}

}


