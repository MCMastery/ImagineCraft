package net.imaginecraft.plugin.listeners.internal.liquidflow;

import net.imaginecraft.plugin.listeners.internal.CancellableEvent;
import org.bukkit.block.Block;

public class BlockMoveEvent implements CancellableEvent {
    private final Block from, to;
    private boolean cancelled;

    BlockMoveEvent(Block from, Block to) {
        this.from = from;
        this.to = to;
        this.cancelled = false;
    }

    public Block getFrom() {
        return this.from;
    }
    public Block getTo() {
        return this.to;
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
