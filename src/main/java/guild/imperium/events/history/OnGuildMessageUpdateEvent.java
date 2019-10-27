package guild.imperium.events.history;

import guild.imperium.commands.api.BotSettings;
import guild.imperium.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class OnGuildMessageUpdateEvent extends ListenerAdapter {

	public void onGuildMessageUpdate(GuildMessageUpdateEvent e) {
		if(e.getGuild().equals(BotSettings.g)) {
			if(e.getMessage().getAttachments().size() > 0) {
				MySQLManager.select("SELECT * FROM message_edits WHERE message_id=?", resultSet -> {
					if(resultSet.next()) {
						int newedits = resultSet.getInt("editamount") + 1;
						if(!(e.getAuthor().isBot())){ editLog(e.getMember(), resultSet.getString("current_message"), e.getMessage().getContentRaw(), e.getChannel(), resultSet.getInt("editamount")); }
						MySQLManager.execute("UPDATE message_edits SET message_id=?, current_message=?, last_edited=NOW(), deleted=0, editamount=?, author_id=?",
								e.getMessageIdLong(),
								e.getMessage().getContentRaw()+ " " + e.getMessage().getAttachments().get(0).getUrl(),
								newedits,
								e.getAuthor().getIdLong());
					}
				}, e.getMessage().getIdLong());

		} else {
				MySQLManager.select("SELECT * FROM message_edits WHERE message_id=?", resultSet -> {
					if(resultSet.next()) {
						int newedits = resultSet.getInt("editamount") + 1;
						if(!(e.getAuthor().isBot())){ editLog(e.getMember(), resultSet.getString("current_message"), e.getMessage().getContentRaw(), e.getChannel(), resultSet.getInt("editamount")); }
						MySQLManager.execute("UPDATE message_edits SET message_id=?, current_message=?, last_edited=NOW(), deleted=0, editamount=?, author_id=?",
								e.getMessageIdLong(),
								e.getMessage().getContentRaw(),
								newedits,
								e.getAuthor().getIdLong());
					}
				}, e.getMessage().getIdLong());
		}
	}


}

private void editLog(Member m, String previous, String newcontent, TextChannel channel, int edits) {
	BotSettings.g.getTextChannelById(BotSettings.msglog).sendMessage(m.getUser().getName() + "#" + m.getUser().getDiscriminator() + " (" + m.getId() + ")" + " has edited his message in: " + channel.getAsMention() + " from `" + previous + "` to: `" + newcontent +"`. This message has been edited " + edits + " times before this.").queue();

}}
