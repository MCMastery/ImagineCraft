package net.imaginecraft.plugin.listeners;

import net.imaginecraft.plugin.listeners.internal.playerjoin.PlayerJoinEvent;
import net.imaginecraft.plugin.listeners.internal.playerjoin.PlayerJoinListener;
import net.imaginecraft.plugin.utils.ConfigUtils;
import net.imaginecraft.plugin.utils.MessageUtils;

public class WelcomeMessenger implements PlayerJoinListener {
    @Override
    public void onPlayerJoin(PlayerJoinEvent evt) {
        if (evt.getPlayer().hasPlayedBefore())
            evt.setMessage(MessageUtils.getWelcomeMessage(evt.getPlayer()));
        else {
            evt.setMessage(MessageUtils.getFirstTimeWelcomeMessage(evt.getPlayer()));
            ConfigUtils.incrementRegisteredPlayers();
        }
    }
}
