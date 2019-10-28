package guild.imperium.utils.redis;

public enum RedisChannel {

        GLOBAL("global"),
        BUKKIT("bukkit"),
        BUNGEE("bungee"),
        BACKEND("backend");

        private String name;

        RedisChannel(String name){
            this.name = "MirrorGames Network-" + name;
        }


        public String getName() {
            return name;
        }
    }
