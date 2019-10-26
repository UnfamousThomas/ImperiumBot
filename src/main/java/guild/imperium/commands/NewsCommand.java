package guild.imperium.commands;

import guild.imperium.commands.api.DiscordCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsCommand extends DiscordCommand {
	public NewsCommand(String rank) {
		super(rank, "news");
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent e) {
		if(args.size() > 3) {
			switch (args.get(1)) {
				case "make":
					args.remove(0);
					args.remove(0);
					break;
				case "add":
					break;
			}
		}
	}

	private void announce(String title, TextChannel channel, String news, Member m) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle(title);
		builder.setColor(Color.decode("#3498db"));
		builder.addField("Content", news, false);
		builder.addField("Date", sdf.format(date), false);
		builder.addField("Time", stf.format(date), false);
		builder.setFooter(m.getEffectiveName(), m.getUser().getEffectiveAvatarUrl());
		channel.sendMessage(builder.build()).queue();
	}
}
