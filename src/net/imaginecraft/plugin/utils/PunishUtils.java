package net.imaginecraft.plugin.utils;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.Warning;

public class PunishUtils {
    private PunishUtils() {}

    public static void warn(Player warner, Player player, String reason) {
        player.addWarning(new Warning(warner.getUniqueId(), player.getUniqueId(), reason));
        player.sendFormattedMessage("&c&lYou were warned by " + warner.getName() + "! Reason: " + reason);
    }
}
