package net.imaginecraft.plugin.listeners;

import net.imaginecraft.plugin.commands.Command;
import net.imaginecraft.plugin.listeners.internal.tabcomplete.TabCompleteEvent;
import net.imaginecraft.plugin.listeners.internal.tabcomplete.TabCompleteListener;
import org.bukkit.Bukkit;

public class CommandTabCompleter implements TabCompleteListener {
    @Override
    public void onTabComplete(TabCompleteEvent evt) {
        if (evt.isCommand()) {
            //todo tab-complete subcommands.
            // we just want to tab-complete command labels.
            if (evt.getBuffer().contains(" "))
                return;

            String label = evt.getBuffer().substring(1); // remove "/"
            for (Command command : Command.getCommands()) {
                String match = command.getMatch(label);
                if (match != null)
                    evt.addCompletion("/" + match);
                Bukkit.broadcastMessage(command.getLabel());
            }
        }
    }
}
