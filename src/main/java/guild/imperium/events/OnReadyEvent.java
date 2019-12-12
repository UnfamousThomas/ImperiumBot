package guild.imperium.events;

import guild.imperium.ImperiumBot;
import guild.imperium.commands.admin.NewsCommand;
import guild.imperium.commands.admin.PictureCommand;
import guild.imperium.commands.api.BotSettings;
import guild.imperium.commands.api.DiscordCommandManager;
import guild.imperium.commands.api.HelpCommand;
import guild.imperium.commands.dev.SecretDevCommand;
import guild.imperium.commands.mod.ClearCommand;
import guild.imperium.commands.admin.CodeCommand;
import guild.imperium.commands.dev.RestartCommand;
import guild.imperium.commands.dev.StopCommand;
import guild.imperium.commands.mod.punish.PunishCommand;
import guild.imperium.commands.mod.punish.PunishLogCommand;
import guild.imperium.events.history.OnGuildMessageDeleteEvent;
import guild.imperium.events.history.OnGuildMessageEvent;
import guild.imperium.events.history.OnGuildMessageUpdateEvent;
import guild.imperium.object.UserObject;
import guild.imperium.utils.Logger;
import guild.imperium.utils.mysql.MySQLManager;
import guild.imperium.utils.redis.RedisManager;
import guild.imperium.utils.redis.payloads.Payload;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.reflections.Reflections;

import javax.annotation.Nonnull;

public class OnReadyEvent implements EventListener {
	@Override
	public void onEvent(@Nonnull GenericEvent event) {
		if(event instanceof ReadyEvent) {
			//Set g:
			BotSettings.g = event.getJDA().getGuildById(588997785861226508L);

			MySQLManager.init("164.132.207.169", "imperiumbot", "discord", "918dHpPBuhLeIcpC");
			RedisManager.init();
			MySQLManager.createTable("member_codes"," `id` INT NOT NULL AUTO_INCREMENT , `code` TEXT NULL , `role` BIGINT NOT NULL , `active` BOOLEAN NOT NULL DEFAULT TRUE , `made_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP , `used_at` DATETIME NULL DEFAULT NULL , PRIMARY KEY (`id`)");
			MySQLManager.createTable("message_edits", " `id` INT NOT NULL AUTO_INCREMENT , `message_id` BIGINT NOT NULL, `current_message` TEXT NOT NULL , `last_edited` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP , `deleted` BOOLEAN NOT NULL, `editamount` INT NOT NULL , `author_id` BIGINT NOT NULL, PRIMARY KEY (`id`) ");
			MySQLManager.createTable("punishment_points", "`id` INT NOT NULL AUTO_INCREMENT , UUID TEXT NOT NULL, `userid` BIGINT NOT NULL , `executor` BIGINT NOT NULL ,`amount` INT NOT NULL , `assigned_at` DATETIME NOT NULL , `expires_at` DATETIME NOT NULL ,`reason` TEXT NOT NULL, `active` BOOLEAN NOT NULL DEFAULT TRUE, PRIMARY KEY (`id`)");
			MySQLManager.createTable("news_list"," `id` INT NOT NULL AUTO_INCREMENT , `member_id` BIGINT NOT NULL , PRIMARY KEY (`id`)");
			DiscordCommandManager.init();
			DiscordCommandManager.registerCommands(
					new CodeCommand("Prince - Chief"),
					new ClearCommand("Duke - Captain"),
					new NewsCommand("Apprentice - Recruit"),
					new HelpCommand("Apprentice - Recruit"),
					new PunishCommand("Master - Captain"),
					new PunishLogCommand("Master - Captain"),
					new RestartCommand("Duke - Captain"),
					new StopCommand("Duke - Captain"),
			        new SecretDevCommand("Emperor - Owner"),
					new PictureCommand("Emperor - Owner")
			);
			//Register listeners:
			JDA jda = event.getJDA();
			jda.addEventListener(new OnGuildMessageEvent());
			jda.addEventListener(new OnVerifyCode());
			jda.addEventListener(new OnGuildMessageUpdateEvent());
			jda.addEventListener(new OnGuildMessageDeleteEvent());
			jda.addEventListener(new onGuildJoinEvent());
			jda.addEventListener(new onGuildLeaveEvent());
			Logger.log(Logger.Level.SUCCESS, "Successfully registered listeners.");


			BotSettings.g.getMembers().forEach(member -> ImperiumBot.getInstance().getManager().addUser(new UserObject(member.getIdLong())));

			for (Class<? extends Payload> clazz : new Reflections("us.unfamousthomas.redis.payloads").getSubTypesOf(Payload.class)) {
				System.out.println("[Redis] Registering " + clazz.getSimpleName());
				RedisManager.registerPayload(clazz);
			}

			BotSettings.g.getTextChannelById(BotSettings.SECRETBOTCOMMANDS).sendMessage("Welcome back! Bot enabled.").queue();

		}
	}
}
