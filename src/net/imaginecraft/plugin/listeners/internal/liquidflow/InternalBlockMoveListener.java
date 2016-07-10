package net.imaginecraft.plugin.listeners.internal.liquidflow;

import net.imaginecraft.plugin.ImagineCraft;
import net.imaginecraft.plugin.listeners.ClaimProtector;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public class InternalBlockMoveListener implements Listener {
    private static Set<BlockMoveListener> listeners = new HashSet<>();

    // only call this for this class, not instances of this class
    public static void register() {
        ImagineCraft.registerListener(new InternalBlockMoveListener());

        // register listeners
        listeners.add(new ClaimProtector());
    }

    @EventHandler
    public void onBlockMove(org.bukkit.event.block.BlockFromToEvent evt) {
        if (evt.isCancelled())
            return;

        BlockMoveEvent event = new BlockMoveEvent(evt.getBlock(), evt.getToBlock());

        // trigger events in listeners
        for (BlockMoveListener listener : listeners) {
            listener.onBlockMove(event);

            // don't pass event on to other listeners once one listener cancels it
            if (event.isCancelled()) {
                evt.setCancelled(true);
                return;
            }
        }
    }
}
