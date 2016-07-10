package net.imaginecraft.plugin.utils;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.commands.Command;
import org.bukkit.Bukkit;
import org.bukkit.help.HelpTopic;

import java.util.ArrayList;
import java.util.List;

public class CommandUtils {
    private CommandUtils() {}

    // returns whether or not a command exists with the given label (in any plugin EXCEPT MINEWARS) (INCLUDES ALIASES)
    public static boolean commandExists(String label) {
        for (HelpTopic cmd : Bukkit.getHelpMap().getHelpTopics()) {
            String l = cmd.getName().substring(1); // remove "/"
            if (l.equalsIgnoreCase(label))
                return true;
        }
        return false;
    }

    public static List<String> getHelpPage(int page, int maxLines, Player player) {
        return StringUtils.getPage(page, maxLines, getHelpBook(player));
    }
    public static List<String> getHelpBook(Player player) {
        List<String> commands = new ArrayList<>();
        // check if description or usage are null because they are for overridden commands
        for (Command command : Command.getCommands())
            if (command.getUsage("") != null && command.getDescription() != null && command.allowExecution(player))
                commands.add(StringUtils.format("&7/" + command.getLabel() + ": &f" + command.getDescription()));
        return commands;
    }


    // complete help book
    public static List<String> getHelpPage(int page, int maxLines) {
        return StringUtils.getPage(page, maxLines, getHelpBook());
    }
    // complete help book
    public static List<String> getHelpBook() {
        List<String> commands = new ArrayList<>();
        for (Command command : Command.getCommands())
            if (command.getUsage("") != null && command.getDescription() != null)
                commands.add(StringUtils.format("&7/" + command.getLabel() + ": &f" + command.getDescription()));
        return commands;
    }
}
