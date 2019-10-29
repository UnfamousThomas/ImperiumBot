package guild.imperium.object;

import guild.imperium.utils.mysql.MySQLManager;

import java.util.ArrayList;
import java.util.List;

public class UserObject {
	public Long id;
	private List<PunishPointObject> punishments = new ArrayList<>();

	public UserObject(Long id) {
		this.id = id;
		MySQLManager.select("SELECT * FROM punishment_points WHERE userid=?", resultSet -> {
			while(resultSet.next()) {

				addPunishments(new PunishPointObject(id, resultSet.getLong("executor"), resultSet.getString("reason"), resultSet.getInt("amount"), resultSet.getString("UUID"), resultSet.getTimestamp("assigned_at"), resultSet.getTimestamp("expires_at")));
			}
		}, id);
	}

	private void addPunishments(PunishPointObject punishPointObject) {
		this.punishments.add(punishPointObject);
		System.out.println(this.punishments);
	}

	public List<PunishPointObject> getPunishments() {
		return this.punishments;
	}
}
