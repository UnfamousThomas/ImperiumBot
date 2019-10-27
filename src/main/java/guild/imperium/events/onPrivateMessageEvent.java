package guild.imperium.events;

import guild.imperium.commands.api.BotSettings;
import guild.imperium.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class onPrivateMessageEvent extends ListenerAdapter {

	public void onMessageReceived(MessageReceivedEvent e) {
		if(e.getChannelType() == ChannelType.PRIVATE && e.getMessage().getContentRaw().startsWith(BotSettings.PREFIX + "role")) {
			String[] args = e.getMessage().getContentRaw().split(" ");
			String code = args[1];

			MySQLManager.select("SELECT * FROM member_codes WHERE code =? AND active = 1", resultSet -> {
				if(resultSet.next()) {
					Long role = resultSet.getLong("role");
					roleAssign(e.getAuthor().getIdLong(), role);
					e.getChannel().sendMessage("Code accepted.").queue();
					MySQLManager.execute("UPDATE member_codes SET code = NULL, active = 0, used_at= NOW()");
				} else {
					e.getChannel().sendMessage("Invalid code").queue();
				}
			}, code
			);
		}
	}

	public void roleAssign(@NotNull Long Userid, @NotNull Long roleId) {
		 Role role =  BotSettings.g.getRoleById(roleId);
		 Member m = BotSettings.g.getMemberById(Userid);
		assert role != null;
		assert m != null;
		BotSettings.g.addRoleToMember(m, role).queue();
	}
}
