package net.imaginecraft.plugin.listeners.internal.tabcomplete;

import net.imaginecraft.plugin.ImagineCraft;
import net.imaginecraft.plugin.listeners.CommandTabCompleter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public class InternalTabCompleteListener implements Listener {
    private static Set<TabCompleteListener> listeners = new HashSet<>();

    // only call this for this class, not instances of this class
    public static void register() {
        ImagineCraft.registerListener(new InternalTabCompleteListener());

        // register listeners
        listeners.add(new CommandTabCompleter());
    }

    @EventHandler
    public void onTabComplete(org.bukkit.event.server.TabCompleteEvent evt) {
        //todo this tab complete is not working
        evt.setCancelled(true);
        /*
        Bukkit.broadcastMessage(evt.getCompletions().toString());
        if (!(evt.getSender() instanceof org.bukkit.entity.Player))
            return;

        evt.getCompletions().clear();

        Player player = Player.getPlayer((org.bukkit.entity.Player) evt.getSender());
        TabCompleteEvent event = new TabCompleteEvent(player, evt.getBuffer(), evt.getCompletions());

        // trigger events in listeners
        for (TabCompleteListener listener : listeners) {
            listener.onTabComplete(event);

            // don't pass event on to other listeners once one listener cancels it
            if (event.isCancelled()) {
                evt.setCancelled(true);
                return;
            }
        }

        evt.setCompletions(event.getCompletions());*/
    }
}
