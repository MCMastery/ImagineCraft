package net.imaginecraft.plugin.listeners;

import net.imaginecraft.plugin.listeners.internal.playerquit.PlayerQuitEvent;
import net.imaginecraft.plugin.listeners.internal.playerquit.PlayerQuitListener;
import net.imaginecraft.plugin.utils.MessageUtils;

public class QuitMessenger implements PlayerQuitListener {
    @Override
    public void onPlayerQuit(PlayerQuitEvent evt) {
        evt.setMessage(MessageUtils.getQuitMessage(evt.getPlayer()));
    }
}
