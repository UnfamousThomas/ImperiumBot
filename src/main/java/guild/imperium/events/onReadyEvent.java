package guild.imperium.events;

import guild.imperium.commands.api.BotSettings;
import guild.imperium.commands.api.DiscordCommandManager;
import guild.imperium.commands.clearCommand;
import guild.imperium.commands.roleAddCommand;
import guild.imperium.events.history.onGuildMessageDeleteEvent;
import guild.imperium.events.history.onGuildMessageEvent;
import guild.imperium.events.history.onGuildMessageUpdateEvent;
import guild.imperium.utils.Logger;
import guild.imperium.utils.mysql.MySQLManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

import javax.annotation.Nonnull;

public class onReadyEvent implements EventListener {
	@Override
	public void onEvent(@Nonnull GenericEvent event) {
		if(event instanceof ReadyEvent) {
			//Set g:
			BotSettings.g = event.getJDA().getGuildById(588997785861226508L);

			MySQLManager.init("localhost", "imperiumbot", "root", null);
			MySQLManager.createTable("member_codes"," `id` INT NOT NULL AUTO_INCREMENT , `code` TEXT NULL , `role` BIGINT NOT NULL , `active` BOOLEAN NOT NULL DEFAULT TRUE , `made_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP , `used_at` DATETIME NULL DEFAULT NULL , PRIMARY KEY (`id`)");
			MySQLManager.createTable("message_edits", " `id` INT NOT NULL AUTO_INCREMENT , `message_id` BIGINT NOT NULL, `current_message` TEXT NOT NULL , `last_edited` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP , `deleted` BOOLEAN NOT NULL, `editamount` INT NOT NULL , `author_id` BIGINT NOT NULL, PRIMARY KEY (`id`) ");
			MySQLManager.createTable("punishment_points", "`id` INT NOT NULL AUTO_INCREMENT , `userid` BIGINT NOT NULL , `amount` INT NOT NULL , `assigned_at` DATETIME NOT NULL , `expires_at` DATETIME NOT NULL ,`reason` TEXT NOT NULL, PRIMARY KEY (`id`)");
			DiscordCommandManager.init();
			DiscordCommandManager.registerCommand(new roleAddCommand("Prince - Chief", "roleadd"), "roleadd");
			DiscordCommandManager.registerCommand(new clearCommand("Duke - Captain"), "clear", "c");

			//Register listeners:
			JDA jda = event.getJDA();
			jda.addEventListener(new onGuildMessageEvent());
			jda.addEventListener(new onPrivateMessageEvent());
			jda.addEventListener(new onGuildMessageUpdateEvent());
			jda.addEventListener(new onGuildMessageDeleteEvent());
			Logger.log(Logger.Level.SUCCESS, "Successfully registered listeners.");


		}
	}
}
