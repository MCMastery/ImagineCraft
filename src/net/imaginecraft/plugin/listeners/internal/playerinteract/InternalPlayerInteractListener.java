package net.imaginecraft.plugin.listeners.internal.playerinteract;

import net.imaginecraft.plugin.ImagineCraft;
import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.listeners.ClaimProtector;
import net.imaginecraft.plugin.listeners.LandClaimer;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public class InternalPlayerInteractListener implements Listener {
    private static Set<PlayerInteractListener> listeners = new HashSet<>();

    // only call this for this class, not instances of this class
    public static void register() {
        ImagineCraft.registerListener(new InternalPlayerInteractListener());

        // register listeners
        listeners.add(new LandClaimer());
        listeners.add(new ClaimProtector());
    }

    @EventHandler
    public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent evt) {
        if (evt.isCancelled())
            return;
        PlayerInteractEvent event = new PlayerInteractEvent(Player.getPlayer(evt.getPlayer()), evt.getAction(), evt.getClickedBlock(), evt.getBlockFace(), evt.getItem(),
                evt.useItemInHand() == Event.Result.ALLOW || evt.useItemInHand() == Event.Result.DEFAULT);

        // trigger events in listeners
        for (PlayerInteractListener listener : listeners) {
            listener.onPlayerInteract(event);

            // don't pass event on to other listeners once one listener cancels it
            if (event.isCancelled()) {
                evt.setCancelled(true);
                return;
            }
        }

        evt.setUseItemInHand(event.useItem() ? Event.Result.ALLOW : Event.Result.DENY);
    }
}
