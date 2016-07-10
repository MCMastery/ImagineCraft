package net.imaginecraft.plugin.listeners.internal.tabcomplete;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.listeners.internal.CancellableEvent;

import java.util.List;

public class TabCompleteEvent implements CancellableEvent {
    private final Player player;
    private final String buffer;
    private List<String> completions;
    private boolean cancelled;

    TabCompleteEvent(Player player, String buffer, List<String> completions) {
        this.player = player;
        this.buffer = buffer;
        this.completions = completions;
        this.cancelled = false;
    }

    public Player getPlayer() {
        return this.player;
    }
    public String getBuffer() {
        return this.buffer;
    }

    public List<String> getCompletions() {
        return this.completions;
    }
    public void addCompletion(String completion) {
        this.completions.add(completion);
    }
    public void removeCompletion(String completion) {
        this.completions.remove(completion);
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCommand() {
        return this.buffer.startsWith("/");
    }
}
