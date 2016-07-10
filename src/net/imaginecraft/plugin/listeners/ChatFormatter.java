package net.imaginecraft.plugin.listeners;

import net.imaginecraft.plugin.listeners.internal.playerchat.PlayerChatEvent;
import net.imaginecraft.plugin.listeners.internal.playerchat.PlayerChatListener;
import net.imaginecraft.plugin.utils.ConfigUtils;

public class ChatFormatter implements PlayerChatListener {
    @Override
    public void onPlayerChat(PlayerChatEvent evt) {
        evt.setMessage(ConfigUtils.formatChat(evt.getMessage(), evt.getPlayer()));
    }
}
