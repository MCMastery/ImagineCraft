package net.imaginecraft.plugin.listeners;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.listeners.internal.playerquit.PlayerQuitEvent;
import net.imaginecraft.plugin.listeners.internal.playerquit.PlayerQuitListener;

public class PlayerUnloader implements PlayerQuitListener {
    @Override
    public void onPlayerQuit(PlayerQuitEvent evt) {
        Player.unload(evt.getPlayer());
    }
}
