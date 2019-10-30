package guild.imperium.events;

import guild.imperium.ImperiumBot;
import guild.imperium.commands.api.BotSettings;
import guild.imperium.object.UserObject;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class onGuildJoinEvent extends ListenerAdapter {

	public void onGuildJoinEvent(GuildJoinEvent e) {
		if(e.getGuild().equals(BotSettings.g)) {
			BotSettings.g.getMembers().forEach(member -> {
				if(ImperiumBot.getInstance().getManager().getUser(member.getIdLong()) == null) {
					ImperiumBot.getInstance().getManager().addUser(new UserObject(member.getIdLong()));
				}
			});
		}
	}
}
