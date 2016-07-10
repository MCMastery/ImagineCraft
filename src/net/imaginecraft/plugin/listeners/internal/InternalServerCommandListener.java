package net.imaginecraft.plugin.listeners.internal;

import net.imaginecraft.plugin.ImagineCraft;
import net.imaginecraft.plugin.commands.Command;
import net.imaginecraft.plugin.utils.CommandUtils;
import net.imaginecraft.plugin.utils.LogUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

//todo listeners (idk if we would really use this event though)
public class InternalServerCommandListener implements Listener {
    public static void register() {
        ImagineCraft.registerListener(new InternalServerCommandListener());
    }

    @EventHandler
    public void onServerCommand(org.bukkit.event.server.ServerCommandEvent evt) {
        // we want to handle this command, not Bukkit! >:D
        evt.setCancelled(true);
        String label = Command.parseAndExecute(evt.getSender(), "/" + evt.getCommand()); // this event doesn't include the "/"
        // label is null if successful, otherwise not null
        if (label != null) {
            if (!CommandUtils.commandExists(label))
                LogUtils.error("Unknown command (" + label + ")!");
            else {
                // this command is not a ImagineCraft command - it is some other plugin's command
                evt.setCancelled(false);
            }
        }
    }
}
