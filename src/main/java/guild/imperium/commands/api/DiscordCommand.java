package guild.imperium.commands.api;

import com.google.common.collect.Maps;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("All")
public abstract class DiscordCommand extends ListenerAdapter {
    Role role;
    final String name;

    public int minArgs = 0;
    public int maxArgs = Integer.MAX_VALUE;

    private final Map<String, DiscordCommand> subcommands = Maps.newHashMap();

    protected String[] aliases = {};
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

    public void executor(MessageReceivedEvent event, List<String> args) {
        if(args.size() > 0) {
            DiscordCommand subcommand = subcommands.get(args.get(0).toLowerCase());
            if(subcommand != null) {
                args.remove(0);
                subcommand.executor(event, args);
                return;
            }
        }

        if(args.size() < minArgs || args.size() > maxArgs) {
            event.getChannel().sendMessage("Invalid usage.").queue();
            return;
        }

        run(event.getMember(), args, event);
    }

    protected void addSubcommands(DiscordCommand... commands) {
        for(DiscordCommand command: commands) {
            subcommands.put(command.name, command);

            for(String alias : command.aliases)
                subcommands.put(alias, command);
        }
    }


public abstract void run(Member m, List<String> args, MessageReceivedEvent e);
}
