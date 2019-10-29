package guild.imperium.object;

import java.sql.Timestamp;

public class PunishPointObject {
	Long userid;
	Long mod;
	String reason;
	Integer points;
	String UUID;
	Timestamp executed;
	Timestamp expired;
	public PunishPointObject(Long userid, Long mod, String reason, Integer points, String UUID, Timestamp executed, Timestamp expired) {
		this.mod = mod;
		this.userid = userid;
		this.reason = reason;
		this.points = points;
		this.UUID = UUID;
		this.executed = executed;
		this.expired = expired;
	}

	public Integer getPoints() {
		return this.points;
	}

	public Long getMod() {
		return mod;
	}

	public Long getUserid() {
		return userid;
	}

	public String getUUID() {
		return UUID;
	}

	public String getReason() {
		return reason;
	}

	public Timestamp getExecuted() {
		return executed;
	}

	public Timestamp getExpired() {
		return expired;
	}
}
