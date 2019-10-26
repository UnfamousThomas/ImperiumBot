package guild.imperium;

import guild.imperium.commands.api.DiscordCommandManager;
import guild.imperium.events.onReadyEvent;
import guild.imperium.utils.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class ImperiumBot {
	public static ImperiumBot instance;
	public static JDA jda;
	public static void main(String[] args) {
		try {
		JDABuilder builder = new JDABuilder("NjM3NzQ0MzAzNDA5NDYzMzI2.XbSoGA.3QmdKKLpbm2f1lzQ5YwKZ3Fa_LE");
		builder.setActivity(Activity.playing("on Wynncraft!"));
		builder.addEventListeners(new onReadyEvent());
		jda = builder.build();

		Logger.log(Logger.Level.SUCCESS, "Successfully connected to discord.");
	} catch (Exception ex) {
		Logger.log(Logger.Level.ERROR, "An error occurred logging into discord.", ex);
		}
	}

	public static JDA getJda() {
		return jda;
	}
}
