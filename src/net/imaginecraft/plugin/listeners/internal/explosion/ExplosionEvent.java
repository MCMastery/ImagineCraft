package net.imaginecraft.plugin.listeners.internal.explosion;

import net.imaginecraft.plugin.listeners.internal.CancellableEvent;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.List;

public class ExplosionEvent implements CancellableEvent {
    private final Location location;
    private List<Block> affectedBlocks;
    private double yield;
    private boolean cancelled;

    ExplosionEvent(Location location, double yield, List<Block> affectedBlocks) {
        this.location = location;
        this.cancelled = false;
        this.yield = yield;
        this.affectedBlocks = affectedBlocks;
    }

    public Location getLocation() {
        return this.location;
    }
    public List<Block> getAffectedBlocks() {
        return this.affectedBlocks;
    }
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
    public double getYield() {
        return this.yield;
    }
    public void setYield(double yield) {
        this.yield = yield;
    }
}
