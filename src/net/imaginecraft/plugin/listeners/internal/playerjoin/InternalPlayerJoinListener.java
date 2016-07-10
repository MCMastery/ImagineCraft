package net.imaginecraft.plugin.listeners.internal.playerjoin;

import net.imaginecraft.plugin.ImagineCraft;
import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.ServerInfoUpdater;
import net.imaginecraft.plugin.listeners.WelcomeMessenger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public class InternalPlayerJoinListener implements Listener {
    private static Set<PlayerJoinListener> listeners = new HashSet<>();

    // only call this for this class, not instances of this class
    public static void register() {
        ImagineCraft.registerListener(new InternalPlayerJoinListener());

        // register listeners
        listeners.add(new WelcomeMessenger());
        listeners.add(new ServerInfoUpdater());
    }

    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent evt) {
        PlayerJoinEvent event = new PlayerJoinEvent(evt.getJoinMessage(), Player.getPlayer(evt.getPlayer()));

        // trigger events in listeners
        for (PlayerJoinListener listener : listeners)
            listener.onPlayerJoin(event);

        // now collect info of what the event's values are now, and edit Bukkit's event accordingly
        evt.setJoinMessage(event.getMessage());
    }
}
