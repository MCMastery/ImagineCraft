package net.imaginecraft.plugin.listeners.internal.explosion;

import net.imaginecraft.plugin.ImagineCraft;
import net.imaginecraft.plugin.listeners.ClaimProtector;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.HashSet;
import java.util.Set;

public class InternalExplosionListener implements Listener {
    private static Set<ExplosionListener> listeners = new HashSet<>();

    // only call this for this class, not instances of this class
    public static void register() {
        ImagineCraft.registerListener(new InternalExplosionListener());

        // register listeners
        listeners.add(new ClaimProtector());
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent evt) {
        if (evt.isCancelled())
            return;

        ExplosionEvent event = new ExplosionEvent(evt.getLocation(), evt.getYield(), evt.blockList());

        // trigger events in listeners
        for (ExplosionListener listener : listeners) {
            listener.onExplosion(event);

            // don't pass event on to other listeners once one listener cancels it
            if (event.isCancelled()) {
                evt.setCancelled(true);
                return;
            }
        }

        // now collect info of what the event's values are now, and edit Bukkit's event accordingly
        evt.setYield((float) event.getYield());
    }
}
