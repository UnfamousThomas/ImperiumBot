package guild.imperium.utils.redis.payloads;


import guild.imperium.utils.redis.RedisChannel;

public class ServerCreatePayload extends Payload{
    String servername;
    Boolean dynamic;
    String image;
    Boolean restrict;

    public ServerCreatePayload(String servername, Boolean restricted, Boolean dynamic, String image) {
        super(RedisChannel.BACKEND);
        this.servername = servername;
        this.dynamic = dynamic;
        this.image = image;
        this.restrict = restricted;
        }

    @Override
    public void onReceive() {

    }
}
