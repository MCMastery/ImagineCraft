package net.imaginecraft.plugin.listeners.internal.entitydamageentity;

import net.imaginecraft.plugin.listeners.internal.CancellableEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageEntity implements CancellableEvent {
    private final Entity entity, damager;
    private final EntityDamageEvent.DamageCause cause;
    private double damage;
    private boolean cancelled;

    EntityDamageEntity(Entity entity, Entity damager, double damage, EntityDamageEvent.DamageCause cause) {
        this.entity = entity;
        this.damager = damager;
        this.damage = damage;
        this.cause = cause;
        this.cancelled = false;
    }

    public Entity getEntity() {
        return this.entity;
    }
    public Entity getDamager() {
        return this.damager;
    }
    public double getDamage() {
        return this.damage;
    }
    public void setDamage(double damage) {
        this.damage = damage;
    }
    public EntityDamageEvent.DamageCause getCause() {
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
