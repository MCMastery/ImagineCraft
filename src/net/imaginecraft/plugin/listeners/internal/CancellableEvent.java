package net.imaginecraft.plugin.listeners.internal;

public interface CancellableEvent {
    boolean isCancelled();
    void setCancelled(boolean cancelled);
}
