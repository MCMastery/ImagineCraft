package net.imaginecraft.plugin.listeners.internal.bucketfill;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.listeners.internal.CancellableEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BucketFillEvent implements CancellableEvent {
    private final Player player;
    private final Block block;
    private final Material bucket;
    private boolean cancelled;

    BucketFillEvent(Player player, Block block, Material bucket) {
        this.player = player;
        this.block = block;
        this.bucket = bucket;
        this.cancelled = false;
    }

    public Player getPlayer() {
        return this.player;
    }
    public Block getBlock() {
        return this.block;
    }
    public Material getBucket() {
        return this.bucket;
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
