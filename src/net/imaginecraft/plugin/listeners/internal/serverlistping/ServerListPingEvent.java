package net.imaginecraft.plugin.listeners.internal.serverlistping;

import java.io.File;
import java.net.InetAddress;

public class ServerListPingEvent {
    private final InetAddress address;
    private final int playerCount;
    private int maxPlayers;
    private String motd;
    private File icon;

    ServerListPingEvent(InetAddress address, int maxPlayers, String motd, int playerCount) {
        this.address = address;
        this.maxPlayers = maxPlayers;
        this.motd = motd;
        this.playerCount = playerCount;
        this.icon = null;
    }

    public InetAddress getAddress() {
        return this.address;
    }
    public int getMaxPlayers() {
        return this.maxPlayers;
    }
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
    public String getMotd() {
        return this.motd;
    }
    public void setMotd(String motd) {
        this.motd = motd;
    }
    public File getIcon() {
        return this.icon;
    }
    public void setIcon(File icon) {
        this.icon = icon;
    }
    public int getPlayerCount() {
        return this.playerCount;
    }
}
