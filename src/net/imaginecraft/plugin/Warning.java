package net.imaginecraft.plugin;

import net.imaginecraft.plugin.utils.StringUtils;

import java.util.UUID;

public class Warning {
    private final UUID warner, player;
    private final String reason;

    public Warning(UUID warner, UUID player, String reason) {
        this.warner = warner;
        this.player = player;
        this.reason = reason;
    }

    public UUID getWarner() {
        return this.warner;
    }
    public UUID getPlayer() {
        return this.player;
    }
    public String getReason() {
        return this.reason;
    }

    public String serialize() {
        return this.warner + "," + this.player + "," + this.reason;
    }
    public static Warning deserialize(String s) {
        String[] split = s.split(",");
        UUID warner = UUID.fromString(split[0]);
        UUID player = UUID.fromString(split[1]);
        String reason = StringUtils.join(split, ",", 2); // what if the reason contains commas?
        return new Warning(warner, player, reason);
    }
}
