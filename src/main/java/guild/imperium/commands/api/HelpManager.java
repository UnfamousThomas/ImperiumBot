package guild.imperium.commands.api;

import com.google.common.collect.Lists;

import java.util.List;

public class HelpManager {
	private List<HelpItem> helpItems = Lists.newArrayList();

	public HelpManager() {

	}

	public void registerHelpItem(DiscordCommand command) {
		HelpItem helpitem = new HelpItem(command);
		helpItems.add(helpitem);
	}

	public String getHelp() {
		StringBuilder builder = new StringBuilder();
		helpItems.forEach(helpitem -> {
			builder.append(BotSettings.PREFIX).append(helpitem.getName()).append(" - ").append(helpitem.getDescription()).append("\n");
		});
		return builder.toString();
	}

	public String getCommandHelp(String name) {
		HelpItem item = null;
		for (HelpItem helpItem: helpItems)
			if(helpItem.getName().equalsIgnoreCase(name))
				item = helpItem;

			if(item == null) {
				return "Command not found - try to find it in !help";
			}
			return  "Name: " + item.getName() +
					"\nDescription: " + item.getDescription() +
					"\nRequired Role: " + item.getCommand().role.getName();
	}

}
