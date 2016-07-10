package net.imaginecraft.plugin.listeners.internal.blockbreak;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.listeners.internal.CancellableEvent;
import org.bukkit.block.Block;

public class BlockBreakEvent implements CancellableEvent {
    private final Player player;
    private final Block block;
    private boolean cancelled;

    BlockBreakEvent(Player player, Block block) {
        this.player = player;
        this.block = block;
        this.cancelled = false;
    }

    public Player getPlayer() {
        return this.player;
    }
    public Block getBlock() {
        return this.block;
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
