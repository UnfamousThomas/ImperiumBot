package guild.imperium.commands.api;

import com.google.common.collect.Maps;
import guild.imperium.ImperiumBot;
import guild.imperium.utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DiscordCommandManager extends ListenerAdapter {

    public Map<String, DiscordCommand> commands = Maps.newHashMap();
    private static DiscordCommandManager instance;
    private HelpManager helpManager;


    public static void registerCommand(DiscordCommand command){
        Logger.log(Logger.Level.INFO, "Attempting to register discord command: " + command.name);
        instance.commands.put(command.name.toLowerCase(), command);

        for(String alias : command.aliases)
            instance.commands.put(alias.toLowerCase(), command);

        instance.helpManager.registerHelpItem(command);
        Logger.log(Logger.Level.SUCCESS, "Registered discord command: " + command.name);

    }

    public static void registerCommands(DiscordCommand... commands){
        for(DiscordCommand command : commands)
            registerCommand(command);

    }

    public static void init(){
        DiscordCommandManager manager = new DiscordCommandManager();
        instance = manager;
        instance.helpManager = new HelpManager();
        ImperiumBot.getJda().addEventListener(manager);


    }

    public void onMessageReceived(MessageReceivedEvent event) {

        String[] argArray = event.getMessage().getContentRaw().split(" ");
        if(!event.getAuthor().isBot()) {
            if (argArray.length != 0 && argArray[0].startsWith(BotSettings.PREFIX)) {
            String commandStr = argArray[0].substring(1);

            List<String> argsList = new ArrayList<>(Arrays.asList(argArray));
                argsList.remove(0);

            if (commands.containsKey(commandStr.toLowerCase())) {
                commands.get(commandStr.toLowerCase()).execute(event, argsList);
            }

        }
    }}
    public static DiscordCommandManager getInstance(){
        return instance;
    }

    public HelpManager getHelpManager() {
        return helpManager;
    }
}