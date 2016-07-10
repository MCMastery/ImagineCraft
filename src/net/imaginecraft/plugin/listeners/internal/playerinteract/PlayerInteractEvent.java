package net.imaginecraft.plugin.listeners.internal.playerinteract;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.listeners.internal.CancellableEvent;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractEvent implements CancellableEvent {
    private final Player player;
    private final Action action;
    private final Block block;
    private final BlockFace blockFace;
    private final ItemStack item;
    private boolean cancelled, useItem;

    PlayerInteractEvent(Player player, Action action, Block block, BlockFace blockFace, ItemStack item, boolean useItem) {
        this.player = player;
        this.action = action;
        this.block = block;
        this.blockFace = blockFace;
        this.item = item;
        this.useItem = useItem;
        this.cancelled = false;
    }

    public Player getPlayer() {
        return this.player;
    }
    public Block getBlock() {
        return this.block;
    }
    public BlockFace getBlockFace() {
        return this.blockFace;
    }
    public Action getAction() {
        return this.action;
    }
    public ItemStack getItem() {
        return this.item;
    }
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
    public boolean useItem() {
        return this.useItem;
    }
    public void setUseItem(boolean useItem) {
        this.useItem = useItem;
    }
}
