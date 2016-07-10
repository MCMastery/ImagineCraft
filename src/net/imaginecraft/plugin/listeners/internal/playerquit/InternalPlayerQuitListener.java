package net.imaginecraft.plugin.listeners.internal.playerquit;

import net.imaginecraft.plugin.ImagineCraft;
import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.ServerInfoUpdater;
import net.imaginecraft.plugin.listeners.GUIEventHandler;
import net.imaginecraft.plugin.listeners.PlayerUnloader;
import net.imaginecraft.plugin.listeners.QuitMessenger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public class InternalPlayerQuitListener implements Listener {
    private static Set<PlayerQuitListener> listeners = new HashSet<>();

    // only call this for this class, not instances of this class
    public static void register() {
        ImagineCraft.registerListener(new InternalPlayerQuitListener());

        // register listeners
        listeners.add(new QuitMessenger());
        listeners.add(new ServerInfoUpdater());
        listeners.add(new PlayerUnloader());
        listeners.add(new GUIEventHandler());
    }

    @EventHandler
    public void onPlayerQuit(org.bukkit.event.player.PlayerQuitEvent evt) {
        PlayerQuitEvent event = new PlayerQuitEvent(evt.getQuitMessage(), Player.getPlayer(evt.getPlayer()));

        // trigger events in listeners
        for (PlayerQuitListener listener : listeners)
            listener.onPlayerQuit(event);

        // now collect info of what the event's values are now, and edit Bukkit's event accordingly
        evt.setQuitMessage(event.getMessage());
    }
}
