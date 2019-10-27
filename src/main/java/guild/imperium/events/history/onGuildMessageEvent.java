package guild.imperium.events.history;

import guild.imperium.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class onGuildMessageEvent extends ListenerAdapter {

	public void onMessageReceived(MessageReceivedEvent e) {
		if(!(e.getAuthor().isBot())) {
			if(e.getMessage().getAttachments().size() > 0) {
			MySQLManager.execute("INSERT into message_edits (message_id, current_message, last_edited, deleted, editamount, author_id) VALUES(?,?,NOW(), 0, 0, ?)",
					e.getMessage().getIdLong(), e.getMessage().getContentRaw() + e.getMessage().getAttachments().get(0).getUrl(), e.getAuthor().getIdLong());
		} else {
				MySQLManager.execute("INSERT into message_edits (message_id, current_message, last_edited, deleted, editamount, author_id) VALUES(?,?,NOW(), 0, 0, ?)",
						e.getMessage().getIdLong(), e.getMessage().getContentRaw(), e.getAuthor().getIdLong());}
		}
	}


}
