package net.imaginecraft.plugin.listeners.internal.guiclick;

import net.imaginecraft.plugin.GUI;
import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.listeners.internal.CancellableEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

public class GUIClickEvent implements CancellableEvent {
    private final Player player;
    private final GUI gui;
    private final InventoryAction action;
    private final int slot;
    private ItemStack item;
    private boolean cancelled;

    GUIClickEvent(ItemStack item, GUI gui, Player player, InventoryAction action, int slot) {
        this.cancelled = false;
        this.gui = gui;
        this.player = player;
        this.action = action;
        this.item = item;
        this.slot = slot;
    }

    public Player getPlayer() {
        return this.player;
    }
    public GUI getGUI() {
        return this.gui;
    }
    public InventoryAction getAction() {
        return this.action;
    }
    public ItemStack getItem() {
        return this.item;
    }
    public void setItem(ItemStack item) {
        this.item = item;
    }
    public int getSlot() {
        return this.slot;
    }
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
