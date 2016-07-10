package net.imaginecraft.plugin.listeners.internal.playerinteractentity;

import net.imaginecraft.plugin.ImagineCraft;
import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.listeners.ClaimProtector;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public class InternalPlayerInteractWithEntityListener implements Listener {
    private static Set<PlayerInteractWithEntityListener> listeners = new HashSet<>();

    // only call this for this class, not instances of this class
    public static void register() {
        ImagineCraft.registerListener(new InternalPlayerInteractWithEntityListener());

        // register listeners
        listeners.add(new ClaimProtector());
    }

    @EventHandler
    public void onPlayerInteractWithEntity(org.bukkit.event.player.PlayerInteractEntityEvent evt) {
        if (evt.isCancelled())
            return;
        PlayerInteractWithEntityEvent event = new PlayerInteractWithEntityEvent(Player.getPlayer(evt.getPlayer()), evt.getRightClicked());

        // trigger events in listeners
        for (PlayerInteractWithEntityListener listener : listeners) {
            listener.onPlayerInteractWithEntity(event);

            // don't pass event on to other listeners once one listener cancels it
            if (event.isCancelled()) {
                evt.setCancelled(true);
                return;
            }
        }
    }
}
