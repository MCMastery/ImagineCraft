package net.imaginecraft.plugin.utils;

import net.imaginecraft.plugin.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class LogUtils {
    public static final ChatColor ERROR_COLOR = ChatColor.RED;
    public static final ChatColor WARNING_COLOR = ChatColor.YELLOW;
    public static final ChatColor INFO_COLOR = ChatColor.BLUE;

    private LogUtils() {}

    public static void info(Object info) {
        String traceInfo = getTraceInfo(2);
        String msg = INFO_COLOR + "[INFO] " + ChatColor.RESET + info + " " + traceInfo;
        Bukkit.getLogger().info(msg);
    }

    public static void error(Object error) {
        String traceInfo = getTraceInfo(2);
        String msg = ERROR_COLOR + "[ERROR] " + ChatColor.RESET + error + " " + traceInfo;

        Bukkit.getLogger().severe(msg);
        for (Player player : Player.getLoadedPlayers())
            if (player.roleAtLeast(Player.Role.OWNER))
                player.sendMessage(msg);
    }

    public static void warning(Object warning) {
        String traceInfo = getTraceInfo(2);
        String msg = WARNING_COLOR + "[WARNING] " + ChatColor.RESET + warning + " " + traceInfo;
        Bukkit.getLogger().warning(msg);
    }

    private static String getTraceInfo(int methodSkip) {
        StackTraceElement stackTrace = new Throwable().getStackTrace()[methodSkip];
        return "(" + stackTrace.getClassName() + ":" + stackTrace.getLineNumber() + ")";
    }
}
