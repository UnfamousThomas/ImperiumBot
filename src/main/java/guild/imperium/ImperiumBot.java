package guild.imperium;

import guild.imperium.events.OnReadyEvent;
import guild.imperium.mangers.UserManager;
import guild.imperium.utils.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class ImperiumBot {
	private static ImperiumBot instance;
	public UserManager manager = new UserManager();
	public static JDA jda;

	public static void main(String[] args) {
		instance = new ImperiumBot();
		try {
		JDABuilder builder = new JDABuilder("NjM3NzQ0MzAzNDA5NDYzMzI2.XbSoGA.3QmdKKLpbm2f1lzQ5YwKZ3Fa_LE");
		builder.setActivity(Activity.playing("on Wynncraft!"));
		builder.addEventListeners(new OnReadyEvent());
		builder.setStatus(OnlineStatus.DO_NOT_DISTURB);

		jda = builder.build();

		Logger.log(Logger.Level.SUCCESS, "Successfully connected to discord.");
	} catch (Exception ex) {
		Logger.log(Logger.Level.ERROR, "An error occurred logging into discord.", ex);
		}
	}

	public static JDA getJda() {
		return jda;
	}

	public static ImperiumBot getInstance() {
		return instance;
	}

	public UserManager getManager() {
		return manager;
	}
}
