package guild.imperium.utils.redis.payloads;


import guild.imperium.utils.redis.RedisChannel;

public class ImperiumRestartPayload extends Payload {

	public ImperiumRestartPayload() {
		super(RedisChannel.BACKEND);
	}

	@Override
	public void onReceive() {
	}

}
