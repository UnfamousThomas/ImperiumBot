package guild.imperium.commands;

import guild.imperium.commands.api.DiscordCommand;
import guild.imperium.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

public class genCodeCommand extends DiscordCommand {

	public genCodeCommand(String rank) {
		super(rank, "gencode");
		description = "Tag a role and get a join code.";
	}

	@Override
	public void run(Member m, List<String> args, MessageReceivedEvent e) {
		if(e.getMessage().getMentionedRoles().size() == 1) {

			long roleId = e.getMessage().getMentionedRoles().get(0).getIdLong();
			AddCode(roleId, e);
			e.getMessage().delete().queue();
		}
	}

	public void AddCode(long id, MessageReceivedEvent e) {
		String code = RandomStringUtils.random(32, true, true);
		MySQLManager.select("SELECT * FROM member_codes WHERE CODE=?", resultSet -> {
			if(!resultSet.next()) {
				MySQLManager.execute("INSERT INTO member_codes (code, role, active, made_at, used_at) VALUES (?,?,?,NOW(),?)",
						code,
						id,
						true,
						null);
				e.getMessage().delete().queue();
				e.getMember().getUser().openPrivateChannel().queue(channel -> {
					channel.sendMessage("Successfully added code, with role id: " + id + ", the code is: ```" + code + "```").queue();
				});
			} else {
				AddCode(id, e);
			}
		}, code);
	}
}
