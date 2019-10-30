package guild.imperium.events;

import guild.imperium.ImperiumBot;
import guild.imperium.commands.api.BotSettings;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class onGuildLeaveEvent extends ListenerAdapter {
	public void onGuildLeaveEvent(GuildLeaveEvent e) {
		if (e.getGuild().equals(BotSettings.g)) {
			ImperiumBot.getInstance().getManager().getUsers().forEach((key, value) -> {
				if(BotSettings.g.getMemberById(key) == null) {
					value = null;
				}
			});

		}
	}
}
