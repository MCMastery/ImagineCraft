package net.imaginecraft.plugin.listeners.internal.bucketempty;

import net.imaginecraft.plugin.ImagineCraft;
import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.listeners.ClaimProtector;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public class InternalBucketEmptyListener implements Listener {
    private static Set<BucketEmptyListener> listeners = new HashSet<>();

    // only call this for this class, not instances of this class
    public static void register() {
        ImagineCraft.registerListener(new InternalBucketEmptyListener());

        // register listeners
        listeners.add(new ClaimProtector());
    }

    @EventHandler
    public void onBucketEmpty(org.bukkit.event.player.PlayerBucketEmptyEvent evt) {
        if (evt.isCancelled())
            return;

        BucketEmptyEvent event = new BucketEmptyEvent(Player.getPlayer(evt.getPlayer()), evt.getBlockClicked(), evt.getBucket());

        // trigger events in listeners
        for (BucketEmptyListener listener : listeners) {
            listener.onBucketEmpty(event);

            // don't pass event on to other listeners once one listener cancels it
            if (event.isCancelled()) {
                evt.setCancelled(true);
                // update inventory to put the liquid back in...
                evt.getPlayer().updateInventory();
                return;
            }
        }
        // now collect info of what the event's values are now, and edit Bukkit's event accordingly
    }
}
