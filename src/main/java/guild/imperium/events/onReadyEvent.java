package guild.imperium.events;

import guild.imperium.commands.api.BotSettings;
import guild.imperium.commands.api.DiscordCommandManager;
import guild.imperium.utils.Logger;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

import javax.annotation.Nonnull;

public class onReadyEvent implements EventListener {
	@Override
	public void onEvent(@Nonnull GenericEvent event) {
		if(event instanceof ReadyEvent) {
			//Register listeners:
			DiscordCommandManager.init();

			//Log:
			Logger.log(Logger.Level.SUCCESS, "Successfully registered listeners.");

			//Set g:
			BotSettings.g = event.getJDA().getGuildById(588997785861226508L);

		}
	}
}
