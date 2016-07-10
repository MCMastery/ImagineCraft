package net.imaginecraft.plugin.listeners.internal.blockbreak;

import net.imaginecraft.plugin.ImagineCraft;
import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.listeners.ClaimProtector;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.HashSet;
import java.util.Set;

public class InternalBlockBreakListener implements Listener {
    private static Set<BlockBreakListener> listeners = new HashSet<>();

    // only call this for this class, not instances of this class
    public static void register() {
        ImagineCraft.registerListener(new InternalBlockBreakListener());

        // register listeners
        listeners.add(new ClaimProtector());
    }

    @EventHandler
    public void onBlockBreak(org.bukkit.event.block.BlockBreakEvent evt) {
        if (evt.isCancelled())
            return;

        BlockBreakEvent event = new BlockBreakEvent(Player.getPlayer(evt.getPlayer()), evt.getBlock());

        // trigger events in listeners
        for (BlockBreakListener listener : listeners) {
            listener.onBlockBreak(event);

            // don't pass event on to other listeners once one listener cancels it
            if (event.isCancelled()) {
                evt.setCancelled(true);
                return;
            }
        }
    }
}
