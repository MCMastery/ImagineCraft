package net.imaginecraft.plugin.utils;

import net.imaginecraft.plugin.ImagineCraft;
import net.imaginecraft.plugin.Player;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.math.BigInteger;
import java.util.List;

public class ConfigUtils {
    static final String MESSAGE_PATH = "messages";
    private static final String PLAYER_COUNT_PATH = "player-count";
    private static final String CHAT_FORMAT_PATH = "chat-format";
    private static final String MESSAGE_FORMAT_PATH = "message-format"; // for private messaging (/msg)
    private static final String TELEPORT_DERAY_PATH = "teleport-delay";
    private static final String SPAWN_PATH = "spawn";
    private static final String LAND_CLAIM_TOOL_PATH = "land-claim-tool";
    private static final String LAND_CLAIM_TOOL_LORE_PATH = "land-claim-tool-lore";
    private static final String CLAIM_BLOCK_PRICE_PATH = "claim-block-price";

    private ConfigUtils() {}

    private static FileConfiguration getConfig() {
        return ImagineCraft.getInstance().getConfig();
    }
    public static void saveConfig() {
        ImagineCraft.getInstance().saveConfig();
    }
    public static void reloadConfig() {
        ImagineCraft.getInstance().reloadConfig();
    }
    public static void resetConfig() {
        File configFile = new File(ImagineCraft.getInstance().getDataFolder(), "config.yml");
        if (!configFile.exists())
            LogUtils.warning("config.yml does not exist! Resetting anyway...");
        else {
            // only delete it if it exists, otherwise just reload it
            if (!configFile.delete())
                LogUtils.error("Could not delete config.yml!");
        }
        ImagineCraft.getInstance().saveDefaultConfig();
        getConfig().options().copyDefaults(true);
    }

    public static void save(String path, Object object) {
        getConfig().set(path, object);
    }
    public static void save(String path, Location location) {
        String serialized = location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
        save(path, serialized);
    }
    public static void save(String path, Material material) {
        save(path, material.name());
    }
    public static void save(String path, BigInteger integer) {
        save(path, integer.toString());
    }

    public static String loadString(String path) {
        return getConfig().getString(path);
    }
    public static int loadInt(String path) {
        return getConfig().getInt(path);
    }
    public static double loadDouble(String path) {
        return getConfig().getDouble(path);
    }
    public static BigInteger loadBigInt(String path) {
        return new BigInteger(getConfig().getString(path));
    }


    public static Location loadLocation(String path) throws NumberFormatException {
        String serialized = loadString(path);
        String[] split = serialized.split(",");
        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]),
                Double.parseDouble(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5]));
    }
    public static Material loadMaterial(String path) {
        return Material.valueOf(getConfig().getString(path));
    }

    public static List<String> loadStringList(String path) {
        return getConfig().getStringList(path);
    }




    public static int getRegisteredPlayers() {
        return loadInt(PLAYER_COUNT_PATH);
    }
    public static void incrementRegisteredPlayers() {
        save(PLAYER_COUNT_PATH, getRegisteredPlayers() + 1);
        saveConfig();
    }
    public static String formatChat(String message, Player player) {
        // color before inserting player name (in case the player name contains color codes)
        String msg = StringUtils.format(ConfigUtils.loadString(CHAT_FORMAT_PATH));
        msg = StringUtils.insertVariables(msg, "playerName", player.getName(),
                "playerRank", player.getRank().getFormattedTag(),
                "playerRole", player.getRole().getFormattedTag(),
                "message", message);
        // what if rank is empty? remove unnecessary spaces
        msg = StringUtils.removeExtraSpaces(msg);
        return msg;
    }


    public static String formatMessage(String message, String sender, String recipient) {
        // color before inserting player name (in case the player name contains color codes)
        String msg = StringUtils.format(ConfigUtils.loadString(MESSAGE_FORMAT_PATH));
        msg = StringUtils.insertVariables(msg, "sender", sender,
                "recipient", recipient,
                "message", message);
        // what if rank is empty? remove unnecessary spaces
        msg = StringUtils.removeExtraSpaces(msg);
        return msg;
    }


    public static Location getSpawn() throws NumberFormatException {
        return loadLocation(SPAWN_PATH);
    }
    public static void setSpawn(Location location) {
        save(SPAWN_PATH, location);
        saveConfig();
    }


    public static double getTeleportDelay() {
        return loadDouble(TELEPORT_DERAY_PATH);
    }
    public static void setTeleportDelay(double delay) {
        save(TELEPORT_DERAY_PATH, delay);
        saveConfig();
    }


    public static Material getLandClaimTool() {
        return loadMaterial(LAND_CLAIM_TOOL_PATH);
    }
    public static void setLandClaimTool(Material tool) {
        save(LAND_CLAIM_TOOL_PATH, tool);
        saveConfig();
    }


    public static String getLandClaimToolLore() {
        return StringUtils.format(loadString(LAND_CLAIM_TOOL_LORE_PATH));
    }
    public static void setLandClaimToolLore(String lore) {
        save(LAND_CLAIM_TOOL_LORE_PATH, lore);
        saveConfig();
    }


    public static BigInteger getClaimBlockPrice() {
        return loadBigInt(CLAIM_BLOCK_PRICE_PATH);
    }
    public static void setClaimBlockPrice(BigInteger price) {
        save(CLAIM_BLOCK_PRICE_PATH, price);
        saveConfig();
    }
}
