package guild.imperium.utils.redis.payloads;


import guild.imperium.utils.redis.RedisChannel;
import guild.imperium.utils.redis.RedisManager;

public abstract class Payload {

    private RedisChannel channel;

    public Payload(RedisChannel channel) {
        this.channel = channel;
    }

    public void publish(){
        RedisManager.publish(channel, this);
    }

    public abstract void onReceive();

}
