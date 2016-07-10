package net.imaginecraft.plugin.listeners.internal.entitydamageentity;

import net.imaginecraft.plugin.ImagineCraft;
import net.imaginecraft.plugin.listeners.ClaimProtector;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashSet;
import java.util.Set;

public class InternalEntityDamageEntityListener implements Listener {
    private static Set<EntityDamageEntityListener> listeners = new HashSet<>();

    // only call this for this class, not instances of this class
    public static void register() {
        ImagineCraft.registerListener(new InternalEntityDamageEntityListener());

        // register listeners
        listeners.add(new ClaimProtector());
    }

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent evt) {
        if (evt.isCancelled())
            return;

        EntityDamageEntity event = new EntityDamageEntity(evt.getEntity(), evt.getDamager(), evt.getDamage(), evt.getCause());

        // trigger events in listeners
        for (EntityDamageEntityListener listener : listeners) {
            listener.onEntityDamageEntity(event);

            // don't pass event on to other listeners once one listener cancels it
            if (event.isCancelled()) {
                evt.setCancelled(true);
                return;
            }
        }

        // now collect info of what the event's values are now, and edit Bukkit's event accordingly
        evt.setDamage(event.getDamage());
    }
}
