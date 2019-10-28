package guild.imperium.events;

import guild.imperium.commands.NewsCommand;
import guild.imperium.commands.api.BotSettings;
import guild.imperium.commands.api.DiscordCommandManager;
import guild.imperium.commands.api.HelpCommand;
import guild.imperium.commands.ClearCommand;
import guild.imperium.commands.CodeCommand;
import guild.imperium.commands.punish.PunishCommand;
import guild.imperium.commands.punish.PunishLogCommand;
import guild.imperium.events.history.OnGuildMessageDeleteEvent;
import guild.imperium.events.history.OnGuildMessageEvent;
import guild.imperium.events.history.OnGuildMessageUpdateEvent;
import guild.imperium.utils.Logger;
import guild.imperium.utils.mysql.MySQLManager;
import guild.imperium.utils.redis.RedisManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

import javax.annotation.Nonnull;

public class OnReadyEvent implements EventListener {
	@Override
	public void onEvent(@Nonnull GenericEvent event) {
		if(event instanceof ReadyEvent) {
			//Set g:
			BotSettings.g = event.getJDA().getGuildById(588997785861226508L);

			MySQLManager.init("localhost", "imperiumbot", "root", null);
			RedisManager.init();
			MySQLManager.createTable("member_codes"," `id` INT NOT NULL AUTO_INCREMENT , `code` TEXT NULL , `role` BIGINT NOT NULL , `active` BOOLEAN NOT NULL DEFAULT TRUE , `made_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP , `used_at` DATETIME NULL DEFAULT NULL , PRIMARY KEY (`id`)");
			MySQLManager.createTable("message_edits", " `id` INT NOT NULL AUTO_INCREMENT , `message_id` BIGINT NOT NULL, `current_message` TEXT NOT NULL , `last_edited` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP , `deleted` BOOLEAN NOT NULL, `editamount` INT NOT NULL , `author_id` BIGINT NOT NULL, PRIMARY KEY (`id`) ");
			MySQLManager.createTable("punishment_points", "`id` INT NOT NULL AUTO_INCREMENT , UUID TEXT NOT NULL, `userid` BIGINT NOT NULL , `executor` BIGINT NOT NULL ,`amount` INT NOT NULL , `assigned_at` DATETIME NOT NULL , `expires_at` DATETIME NOT NULL ,`reason` TEXT NOT NULL, PRIMARY KEY (`id`)");
			MySQLManager.createTable("news_list"," `id` INT NOT NULL AUTO_INCREMENT , `member_id` BIGINT NOT NULL , PRIMARY KEY (`id`)");
			DiscordCommandManager.init();
			DiscordCommandManager.registerCommand(new CodeCommand("Prince - Chief"), "code");
			DiscordCommandManager.registerCommand(new ClearCommand("Duke - Captain"), "clear", "c");
			DiscordCommandManager.registerCommand(new NewsCommand("Apprentice - Recruit"), "news","announce");
			DiscordCommandManager.registerCommand(new HelpCommand("Apprentice - Recruit"), "help");
			DiscordCommandManager.registerCommand(new PunishCommand("Master - Captain"), "punish", "p");
			DiscordCommandManager.registerCommand(new PunishLogCommand("Master - Captain"), "punishlog", "pl");

			//Register listeners:
			JDA jda = event.getJDA();
			jda.addEventListener(new OnGuildMessageEvent());
			jda.addEventListener(new OnVerifyCode());
			jda.addEventListener(new OnGuildMessageUpdateEvent());
			jda.addEventListener(new OnGuildMessageDeleteEvent());
			Logger.log(Logger.Level.SUCCESS, "Successfully registered listeners.");


		}
	}
}