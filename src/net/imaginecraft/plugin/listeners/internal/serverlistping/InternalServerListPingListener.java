package net.imaginecraft.plugin.listeners.internal.serverlistping;

import net.imaginecraft.plugin.ImagineCraft;
import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.listeners.PlayerUnloader;
import net.imaginecraft.plugin.listeners.QuitMessenger;
import net.imaginecraft.plugin.listeners.ServerInfo;
import net.imaginecraft.plugin.utils.LogUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.CachedServerIcon;

import java.util.HashSet;
import java.util.Set;

public class InternalServerListPingListener implements Listener {
    private static Set<ServerListPingListener> listeners = new HashSet<>();

    // only call this for this class, not instances of this class
    public static void register() {
        ImagineCraft.registerListener(new InternalServerListPingListener());

        // register listeners
        listeners.add(new ServerInfo());
    }

    @EventHandler
    public void onServerListPing(org.bukkit.event.server.ServerListPingEvent evt) {
        ServerListPingEvent event = new ServerListPingEvent(evt.getAddress(), evt.getMaxPlayers(), evt.getMotd(), evt.getNumPlayers());

        // trigger events in listeners
        for (ServerListPingListener listener : listeners)
            listener.onServerListPing(event);

        // now collect info of what the event's values are now, and edit Bukkit's event accordingly
        evt.setMaxPlayers(event.getMaxPlayers());
        evt.setMotd(event.getMotd());
        if (event.getIcon() != null) {
            try {
                evt.setServerIcon(Bukkit.loadServerIcon(event.getIcon()));
            } catch (Exception e) {
                LogUtils.error("Exception while attempting to load server icon!");
                e.printStackTrace();
            }
        }
    }
}
