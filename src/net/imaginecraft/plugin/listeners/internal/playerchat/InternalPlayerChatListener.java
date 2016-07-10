package net.imaginecraft.plugin.listeners.internal.playerchat;

import net.imaginecraft.plugin.ImagineCraft;
import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.listeners.ChatFormatter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public class InternalPlayerChatListener implements Listener {
    private static Set<PlayerChatListener> listeners = new HashSet<>();

    // only call this for this class, not instances of this class
    public static void register() {
        ImagineCraft.registerListener(new InternalPlayerChatListener());

        // register listeners
        listeners.add(new ChatFormatter());
    }

    @EventHandler
    public void onPlayerChat(org.bukkit.event.player.AsyncPlayerChatEvent evt) {
        if (evt.isCancelled())
            return;

        PlayerChatEvent event = new PlayerChatEvent(evt.getMessage(), Player.getPlayer(evt.getPlayer()));

        // trigger events in listeners
        for (PlayerChatListener listener : listeners) {
            listener.onPlayerChat(event);

            // don't pass event on to other listeners once one listener cancels it
            if (event.isCancelled()) {
                evt.setCancelled(true);
                return;
            }
        }

        // now collect info of what the event's values are now, and edit Bukkit's event accordingly
        evt.setMessage(event.getMessage());
        // we want to use a single message thing.
        evt.setFormat(evt.getMessage());
    }
}
