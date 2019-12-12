package guild.imperium.commands.admin;

import guild.imperium.commands.api.BotSettings;
import guild.imperium.commands.api.DiscordCommand;
import guild.imperium.commands.api.RoleHierarchy;
import guild.imperium.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PictureCommand extends DiscordCommand {
	public PictureCommand(String rank) {
		super(rank, "pic");
		minArgs = 2;
		description = "Just used to get your profile pic link.";
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent e) {
			e.getChannel().sendMessage(m.getUser().getEffectiveAvatarUrl()).queue();
		}

}
