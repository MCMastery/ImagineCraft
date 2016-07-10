package net.imaginecraft.plugin.listeners.internal.playerchat;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.listeners.internal.CancellableEvent;

public class PlayerChatEvent implements CancellableEvent {
    private final Player player;
    private String message;
    private boolean cancelled;

    PlayerChatEvent(String message, Player player) {
        this.message = message;
        this.player = player;
        this.cancelled = false;
    }

    public Player getPlayer() {
        return this.player;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
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
