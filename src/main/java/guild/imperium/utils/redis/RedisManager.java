package guild.imperium.utils.redis;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import guild.imperium.utils.Logger;
import guild.imperium.utils.redis.payloads.Payload;
import guild.imperium.utils.redis.payloads.PingPayload;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Map;

@SuppressWarnings("Duplicates")
public class RedisManager  {

    private final String REDIS_IP = "164.132.207.169";
    private static RedisManager instance;
    private Map<String, Class<? extends Payload>> payloads = Maps.newHashMap();
    private Gson gson;

    /**
     * Initialise Redis.
     */
    public static void init(){
        instance = new RedisManager();

        instance.subscribe();
        instance.gson = new Gson();
        Logger.log(Logger.Level.INFO, "Redis PUB SUB Enabled!");
    }


    /**
     * Convert a {@link Payload} to JSON.
     * @param payload The payload to convert.
     * @return Converted Payload as a String.
     */
    private String toJson(Payload payload){
        JSONObject object = new JSONObject();

        object.put("name", payload.getClass().getSimpleName());
        object.put("data", gson.toJson(payload));

        return object.toString();
    }

    private Payload fromJson(String message){

        JSONObject object = new JSONObject(message);

        String name = object.getString("name");
        String data = object.getString("data");

        if(payloads.containsKey(name))
            return gson.fromJson(data, payloads.get(name));

        return new PingPayload();

    }

    public static void registerPayload(Class<? extends Payload> payload){
        instance.payloads.put(payload.getSimpleName(), payload);
    }

    @SafeVarargs
    public static void registerPayloads(Class<? extends Payload>... payloads){
        for(Class<? extends Payload> payload : payloads)
            registerPayload(payload);

    }


    /**
     * Subscribe to the relevant Redis channels.
     */
    private void subscribe(){

        Jedis subscriber = new Jedis(REDIS_IP);
        subscriber.connect();

        new Thread("Redis Subscriber"){
            @Override
            public void run(){
                String[] channels = {RedisChannel.BACKEND.getName(), RedisChannel.GLOBAL.getName(), RedisChannel.BUNGEE.getName()};
                subscriber.connect();
                subscriber.subscribe(new JedisPubSub(){
                    @Override
                    public void onMessage(String channel, String message){
                        fromJson(message).onReceive();
                    }
                }, channels);
            }
        }.start();
    }

    /**
     * Publish a message to Redis.
     * @param channel The {@link RedisChannel} to publish to.
     * @param payload The {@link Payload} to publish.
     */
    public static void publish(RedisChannel channel, Payload payload){
        Logger.log(Logger.Level.INFO, "Publishing Payload to " + channel.getName() + "!");

        Jedis publisher = new Jedis(instance.REDIS_IP);
        publisher.publish(channel.getName(), instance.toJson(payload));
    }

}
