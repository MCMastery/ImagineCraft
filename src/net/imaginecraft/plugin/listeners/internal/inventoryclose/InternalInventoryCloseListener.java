package net.imaginecraft.plugin.listeners.internal.inventoryclose;

import net.imaginecraft.plugin.ImagineCraft;
import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.listeners.GUIEventHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public class InternalInventoryCloseListener implements Listener {
    private static Set<InventoryCloseListener> listeners = new HashSet<>();

    // only call this for this class, not instances of this class
    public static void register() {
        ImagineCraft.registerListener(new InternalInventoryCloseListener());

        // register listeners
        listeners.add(new GUIEventHandler());
    }

    @EventHandler
    public void onInventoryClose(org.bukkit.event.inventory.InventoryCloseEvent evt) {
        InventoryCloseEvent event = new InventoryCloseEvent(Player.getPlayer((org.bukkit.entity.Player) evt.getPlayer()), evt.getInventory());

        // trigger events in listeners
        for (InventoryCloseListener listener : listeners)
            listener.onInventoryClose(event);
    }
}
