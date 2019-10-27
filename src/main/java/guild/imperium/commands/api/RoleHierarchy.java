package guild.imperium.commands.api;


import net.dv8tion.jda.api.entities.Role;

public class RoleHierarchy {
    public static int getHierarchy(Role role) {
        if(role.equals(BotSettings.g.getRolesByName("Distiguished Guest", true).get(0))) { return 0; }
        else if(role.equals(BotSettings.g.getRolesByName("Merchant - Recruit", true).get(0))) { return 1; }
        else if(role.equals(BotSettings.g.getRolesByName("Apprentice - Recruit", true).get(0))) { return 2; }
        else if(role.equals(BotSettings.g.getRolesByName("Knight - Recruiter", true).get(0))) { return 3; }
        else if(role.equals(BotSettings.g.getRolesByName("Officer - Recruiter", true).get(0))) { return 4; }
        else if(role.equals(BotSettings.g.getRolesByName("Master - Captain", true).get(0))) { return 5; }
        else if(role.equals(BotSettings.g.getRolesByName("Duke - Captain", true).get(0))) { return 6; }
        else if(role.equals(BotSettings.g.getRolesByName("Prince - Chief", true).get(0))) { return 7; }
        else if(role.equals(BotSettings.g.getRolesByName("Emperor - Owner", true).get(0))) { return 8; }
        else return -1;
    }
    public static boolean isHigherorEqualsRole(Role role, Role target) {
        return getHierarchy(role) >= getHierarchy(target);
    }
}
