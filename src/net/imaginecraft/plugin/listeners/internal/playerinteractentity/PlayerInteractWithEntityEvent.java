package net.imaginecraft.plugin.listeners.internal.playerinteractentity;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.listeners.internal.CancellableEvent;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractWithEntityEvent implements CancellableEvent {
    private final Player player;
    private final Entity entity;
    private boolean cancelled;

    PlayerInteractWithEntityEvent(Player player, Entity entity) {
        this.player = player;
        this.entity = entity;
        this.cancelled = false;
    }

    public Player getPlayer() {
        return this.player;
    }
    public Entity getEntity() {
        return this.entity;
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
