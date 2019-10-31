package guild.imperium.commands.mod;

import guild.imperium.commands.api.BotSettings;
import guild.imperium.commands.api.DiscordCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClearCommand extends DiscordCommand {
	public ClearCommand(String rank) {
		super(rank, "clear");
		description = "Allows to clear the chat.";
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent e) {
		if(args.size()==2) {
			List<Message> msgs;
			MessageHistory history = new MessageHistory(e.getChannel());
			try {
				int size = Integer.parseInt(args.get(1));
				msgs = history.retrievePast(size).complete();
				msgs.forEach(message -> {
					message.delete().queue();
				});
				e.getMessage().delete().queue();
				log(size, e.getTextChannel(), m);
			} catch (NumberFormatException ex) {
				e.getChannel().sendMessage("Invalid formatting.").queue(message -> message.delete().queueAfter(15, TimeUnit.SECONDS));}
		}}
	public void log(int amount, TextChannel channel, Member moderator) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("LOG - CLEAR");
		builder.setColor(Color.decode("#f1c40f"));
		builder.setThumbnail(moderator.getUser().getEffectiveAvatarUrl());
		builder.addField("Channel", channel.getAsMention(), false);
		builder.addField("Number of messages cleared", String.valueOf(amount), false);
		builder.addField("Clearer", moderator.getAsMention(), false);
		builder.addField("Date", sdf.format(date), false);
		builder.addField("Time", stf.format(date), false);
		BotSettings.g.getTextChannelById(BotSettings.MODLOG).sendMessage(builder.build()).queue();
	}
}
