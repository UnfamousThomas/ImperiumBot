package guild.imperium.commands.api;

public class HelpItem {

	private final String name, description;
	private final DiscordCommand command;

	public HelpItem(DiscordCommand command){
		this.command = command;
		this.name = command.name;
		this.description = command.description;
	}

	public String getName() {
		return name;
	}

	public DiscordCommand getCommand() {
		return command;
	}

	public String getDescription() {
		return description;
	}
}
