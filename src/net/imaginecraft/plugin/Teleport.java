package net.imaginecraft.plugin;

import net.imaginecraft.plugin.utils.ConfigUtils;
import net.imaginecraft.plugin.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public abstract class Teleport {
    private final Location start;
    private final Player player;
    private final double delay, distanceThreshold;
    private boolean complete;
    private int taskId;

    public Teleport(Player player) {
        this(player, ConfigUtils.getTeleportDelay());
    }
    public Teleport(Player player, double delay) {
        this.start = player.getLocation();
        this.player = player;
        this.delay = delay;
        this.distanceThreshold = 0.5;
        this.complete = false;
    }

    public double getDistanceThreshold() {
        return this.distanceThreshold;
    }
    public Location getStartLocation() {
        return this.start;
    }
    public Player getPlayer() {
        return this.player;
    }
    public double getDelay() {
        return this.delay;
    }
    public boolean isComplete() {
        return this.complete;
    }
    public void cancel() {
        this.complete = true;
    }
    // called before when teleportation commences
    public abstract Location getDestination();

    public void start() {
        // check for player movement
        this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(ImagineCraft.getInstance(), () -> {
            if (this.complete) {
                Bukkit.getScheduler().cancelTask(this.taskId);
                return;
            }
            if (this.player.getLocation().distance(this.start) >= this.distanceThreshold) {
                this.player.sendMessage(MessageUtils.getTeleportationCancelledMessage());
                cancel();
            }
        }, 0, Math.round(10)); // twice per second

        Bukkit.getScheduler().scheduleSyncDelayedTask(ImagineCraft.getInstance(), () -> {
            if (this.complete)
                return;
            this.player.setLocation(getDestination());
            this.player.sendMessage(MessageUtils.getTeleportationCompletedMessage());
            this.complete = true;
        }, Math.round(this.delay * 20)); // 20 ticks per second
    }
}
