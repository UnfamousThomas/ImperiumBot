package guild.imperium.commands.api;

import com.google.common.collect.Maps;
import guild.imperium.ImperiumBot;
import guild.imperium.utils.Logger;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DiscordCommandManager extends ListenerAdapter {

    private Map<String, DiscordCommand> commands = Maps.newHashMap();
    private static DiscordCommandManager instance;


    public static void registerCommand(DiscordCommand command, String name, String... aliases){
        Logger.log(Logger.Level.INFO, "Attempting to register discord command: " + name);
        instance.commands.put(name.toLowerCase(), command);

        for(String alias : aliases)
            instance.commands.put(alias.toLowerCase(), command);

        Logger.log(Logger.Level.INFO, "Registered discord command: " + name);

    }

    public static void init(){
        DiscordCommandManager manager = new DiscordCommandManager();
        instance = manager;
        ImperiumBot.getJda().addEventListener(manager);


    }

    public void onMessageReceived(MessageReceivedEvent event) {

        String[] argArray = event.getMessage().getContentRaw().split(" ");
        if(!event.getAuthor().isBot()) {
            if (argArray.length != 0 && argArray[0].startsWith(BotSettings.PREFIX) && !(event.getAuthor().isBot())) {
            String commandStr = argArray[0].substring(1);

            List<String> argsList = new ArrayList<>(Arrays.asList(argArray));

            if (commands.containsKey(commandStr.toLowerCase())) {
                commands.get(commandStr.toLowerCase()).execute(event, argsList);
            }

        }
    }}
    public static DiscordCommandManager getInstance(){
        return instance;
    }

}