package net.imaginecraft.plugin;

import net.imaginecraft.plugin.utils.MessageUtils;

public class TeleportRequest {
    private final Player player, target;
    private boolean cancelled;

    public TeleportRequest(Player player, Player target) {
        this.player = player;
        this.target = target;
        this.cancelled = false;
    }

    public Player getPlayer() {
        return this.player;
    }
    public Player getTarget() {
        return this.target;
    }
    public boolean isCancelled() {
        return this.cancelled;
    }
    public void cancel() {
        this.cancelled = true;
        complete();
    }
    public void complete() {
        this.target.setPendingRequest(null);
        this.player.setPendingRequest(null);
    }

    // initiates the request
    public void request() {
        this.player.sendMessage(MessageUtils.getTeleportRequestMessage(this.target));
        this.target.sendMessage(MessageUtils.getTeleportRequestTargetMessage(this.player));
        this.player.setPendingRequest(this);
        this.target.setPendingRequest(this);
    }

    public void accept() {
        this.player.sendMessage(MessageUtils.getTeleportMessage(this.target.getName()));
        this.player.teleport(this.target);
        complete();
    }
    public void deny() {
        this.cancelled = true;
        this.player.sendMessage(MessageUtils.getTeleportRequestDeniedMessage());
        complete();
    }
}
