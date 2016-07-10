package net.imaginecraft.plugin.listeners.internal.inventoryclose;

import net.imaginecraft.plugin.Player;
import org.bukkit.inventory.Inventory;

public class InventoryCloseEvent {
    private final Player player;
    private final Inventory inventory;

    InventoryCloseEvent(Player player, Inventory inventory) {
        this.inventory = inventory;
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }
    public Inventory getInventory() {
        return this.inventory;
    }
}
