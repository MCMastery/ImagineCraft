package net.imaginecraft.plugin.listeners.internal.entitybreakhanging;

import net.imaginecraft.plugin.listeners.internal.CancellableEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Hanging;
import org.bukkit.event.hanging.HangingBreakEvent;

public class EntityBreakHangingEvent implements CancellableEvent {
    private final Entity entity;
    private final Hanging hanging;
    private final HangingBreakEvent.RemoveCause cause;
    private boolean cancelled;

    EntityBreakHangingEvent(Entity entity, Hanging hanging, HangingBreakEvent.RemoveCause cause) {
        this.entity = entity;
        this.hanging = hanging;
        this.cause = cause;
        this.cancelled = false;
    }

    public Entity getEntity() {
        return this.entity;
    }
    public Hanging getHanging() {
        return this.hanging;
    }
    public HangingBreakEvent.RemoveCause getCause() {
        return this.cause;
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
