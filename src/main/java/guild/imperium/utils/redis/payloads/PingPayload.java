package guild.imperium.utils.redis.payloads;


import guild.imperium.utils.redis.RedisChannel;

public class PingPayload extends Payload {

    public PingPayload() {
        super(RedisChannel.BACKEND);
    }

    @Override
    public void onReceive() {
        System.out.println("Pong");
    }
}