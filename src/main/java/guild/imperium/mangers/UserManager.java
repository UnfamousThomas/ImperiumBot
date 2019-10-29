package guild.imperium.mangers;

import guild.imperium.ImperiumBot;
import guild.imperium.commands.api.BotSettings;
import guild.imperium.object.UserObject;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
	private Map<Long, UserObject> userObjectLongMap = new HashMap<>();

	public void addUser(UserObject user) {
		userObjectLongMap.put(user.id, user);
	}

	public Map<Long, UserObject> getUsers() {
		return userObjectLongMap;
	}

	public UserObject getUser(long id) {
		return userObjectLongMap.get(id);
	}

	public void ResetUsers() {
		for (UserObject value : userObjectLongMap.values()) {
			value = null;
		}
		userObjectLongMap = null;
		BotSettings.g.getMembers().forEach(member -> ImperiumBot.getInstance().getManager().addUser(new UserObject(member.getIdLong())));

	}
	}
