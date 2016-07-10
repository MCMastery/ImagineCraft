package net.imaginecraft.plugin.listeners.internal.entitybreakhanging;

import net.imaginecraft.plugin.ImagineCraft;
import net.imaginecraft.plugin.listeners.ClaimProtector;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

import java.util.HashSet;
import java.util.Set;

public class InternalEntityBreakHangingListener implements Listener {
    private static Set<EntityBreakHangingListener> listeners = new HashSet<>();

    // only call this for this class, not instances of this class
    public static void register() {
        ImagineCraft.registerListener(new InternalEntityBreakHangingListener());

        // register listeners
        listeners.add(new ClaimProtector());
    }

    @EventHandler
    public final void onEntityBreakHanging(HangingBreakByEntityEvent evt) {
        if (evt.isCancelled())
            return;

        EntityBreakHangingEvent event = new EntityBreakHangingEvent(evt.getRemover(), evt.getEntity(), evt.getCause());

        // trigger events in listeners
        for (EntityBreakHangingListener listener : listeners) {
            listener.onEntityBreakHanging(event);

            // don't pass event on to other listeners once one listener cancels it
            if (event.isCancelled()) {
                evt.setCancelled(true);
                return;
            }
        }
    }
}
