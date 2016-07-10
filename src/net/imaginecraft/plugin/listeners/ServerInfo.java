package net.imaginecraft.plugin.listeners;

import net.imaginecraft.plugin.listeners.internal.serverlistping.ServerListPingEvent;
import net.imaginecraft.plugin.listeners.internal.serverlistping.ServerListPingListener;
import net.imaginecraft.plugin.utils.MessageUtils;

import java.util.List;
import java.util.Random;

public class ServerInfo implements ServerListPingListener {
    @Override
    public void onServerListPing(ServerListPingEvent evt) {
        List<String> motds = MessageUtils.getMotds();
        if (motds.size() > 0)
            evt.setMotd(motds.get(new Random().nextInt(motds.size())));
    }
}
