package guild.imperium.events.history;

import guild.imperium.commands.api.BotSettings;
import guild.imperium.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class onGuildMessageDeleteEvent extends ListenerAdapter {
	public void onGuildMessageDelete(GuildMessageDeleteEvent e) {
		MySQLManager.select("SELECT * FROM message_edits WHERE message_id =?", resultSet -> {
			if(resultSet.next()) {
				editLog(e.getChannel(), resultSet.getInt("editamount"), resultSet.getString("current_message"));
				MySQLManager.execute("UPDATE message_edits SET deleted=?, last_edited= NOW() WHERE message_id=?", true, e.getMessageIdLong());
			}
		}, e.getMessageIdLong());
	}

	private void editLog(TextChannel channel, int edits, String message) {
		BotSettings.g.getTextChannelById(BotSettings.msglog).sendMessage("A message was deleted a message in: " + channel.getAsMention() + "The message was:`" + message + "`. The message was edited " + edits + " times before this.").queue();

	}

}
