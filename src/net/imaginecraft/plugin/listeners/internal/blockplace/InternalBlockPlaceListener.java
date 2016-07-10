package net.imaginecraft.plugin.listeners.internal.blockplace;

import net.imaginecraft.plugin.ImagineCraft;
import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.listeners.ClaimProtector;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public class InternalBlockPlaceListener implements Listener {
    private static Set<BlockPlaceListener> listeners = new HashSet<>();

    // only call this for this class, not instances of this class
    public static void register() {
        ImagineCraft.registerListener(new InternalBlockPlaceListener());

        // register listeners
        listeners.add(new ClaimProtector());
    }

    @EventHandler
    public void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent evt) {
        if (evt.isCancelled())
            return;

        BlockPlaceEvent event = new BlockPlaceEvent(Player.getPlayer(evt.getPlayer()), evt.getBlock(), evt.getBlockReplacedState().getBlock(), evt.canBuild());

        // trigger events in listeners
        for (BlockPlaceListener listener : listeners) {
            listener.onBlockPlace(event);

            // don't pass event on to other listeners once one listener cancels it
            if (event.isCancelled()) {
                evt.setCancelled(true);
                return;
            }
        }
        // now collect info of what the event's values are now, and edit Bukkit's event accordingly
        evt.setBuild(event.canBuild());
    }
}
