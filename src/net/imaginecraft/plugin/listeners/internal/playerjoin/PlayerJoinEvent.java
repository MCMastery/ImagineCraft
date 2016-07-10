package net.imaginecraft.plugin.listeners.internal.playerjoin;

import net.imaginecraft.plugin.Player;

public class PlayerJoinEvent {
    private final Player player;
    private String message;

    PlayerJoinEvent(String message, Player player) {
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
