package net.imaginecraft.plugin.listeners.internal.playerquit;

import net.imaginecraft.plugin.Player;

public class PlayerQuitEvent {
    private final Player player;
    private String message;

    PlayerQuitEvent(String message, Player player) {
        this.message = message;
        this.player = player;
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
}
