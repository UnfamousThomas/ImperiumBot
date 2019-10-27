package guild.imperium.commands.api;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("All")
public abstract class DiscordCommand extends ListenerAdapter {
    Role role;
    final String name;

    public int minArgs = 0;
    public int maxArgs = Integer.MAX_VALUE;

    protected String description = "No description set. Sorry!";


    public DiscordCommand(String rank, String name) {
        this.role = BotSettings.g.getRolesByName(rank, true).get(0);
        this.name = name;
    }

    public void execute(MessageReceivedEvent e, List<String> args) {
        if(args.size() > maxArgs || args.size() < minArgs ) { e.getChannel().sendMessage("Invalid args."); return; }
        if(!(e.getChannelType().isGuild())) return;
            if(RoleHierarchy.isHigherorEqualsRole(e.getMember().getRoles().get(0), role)) {
                run(e.getMember(), args, e);
            } else {
            e.getChannel().sendMessage("Insufficient permissions for that command.").queue(message -> message.delete().queueAfter(1, TimeUnit.MINUTES));
            }
    }


public abstract void run(Member m, List<String> args, MessageReceivedEvent e);
}
