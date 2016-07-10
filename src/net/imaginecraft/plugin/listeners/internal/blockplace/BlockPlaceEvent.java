package net.imaginecraft.plugin.listeners.internal.blockplace;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.listeners.internal.CancellableEvent;
import org.bukkit.block.Block;

public class BlockPlaceEvent implements CancellableEvent {
    private final Player player;
    private final Block block, oldBlock;
    private boolean cancelled, canBuild;

    BlockPlaceEvent(Player player, Block block, Block oldBlock, boolean canBuild) {
        this.player = player;
        this.block = block;
        this.oldBlock = oldBlock;
        this.canBuild = canBuild;
        this.cancelled = false;
    }

    public Player getPlayer() {
        return this.player;
    }
    public Block getBlock() {
        return this.block;
    }
    public Block getOldBlock() {
        return this.oldBlock;
    }
    public boolean canBuild() {
        return this.canBuild;
    }
    public void setCanBuild(boolean canBuild) {
        this.canBuild = canBuild;
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
