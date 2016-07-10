package net.imaginecraft.plugin.utils;

import net.imaginecraft.plugin.ImagineCraft;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    private static FileConfiguration bukkitYml = null, worldData;
    private static File bukkitYmlFileSource = null, worldDataSource;

    private FileUtils() {}

    public static File getServerFolder() {
        return new File(".");
    }
    public static File getBackupFolder() {
        File folder = new File("backup");
        if (!folder.exists())
            if (!folder.mkdir())
                LogUtils.error("Could not create backup folder! Do it manually.");
        return folder;
    }


    public static void reloadWorldData() {
        String path = "worlds.yml";
        if (worldDataSource == null)
            worldDataSource = new File(ImagineCraft.getInstance().getDataFolder(), path);
        worldData = YamlConfiguration.loadConfiguration(worldDataSource);
    }
    public static FileConfiguration getWorldData() {
        if (worldData == null)
            reloadWorldData();
        return worldData;
    }
    public static void saveWorldData() {
        if (worldData == null || worldDataSource == null)
            return;
        try {
            getWorldData().save(worldDataSource);
        } catch (IOException e) {
            LogUtils.error("IOException when attempting to save claim data file!");
            e.printStackTrace();
        }
    }


    public static void reloadBukkitYml() {
        String path = "bukkit.yml";
        if (bukkitYmlFileSource == null)
            bukkitYmlFileSource = new File(getServerFolder(), path);
        bukkitYml = YamlConfiguration.loadConfiguration(bukkitYmlFileSource);
    }
    public static FileConfiguration getBukkitYml() {
        if (bukkitYml == null)
            reloadBukkitYml();
        return bukkitYml;
    }
    public static void saveBukkitYml() {
        if (bukkitYml == null || bukkitYmlFileSource == null)
            return;
        try {
            getBukkitYml().save(bukkitYmlFileSource);
        } catch (IOException e) {
            LogUtils.error("IOException when attempting to save claim data file!");
            e.printStackTrace();
        }
    }

    public static void registerBukkitYmlWorld(String worldName, String id) {
        String string = ImagineCraft.getInstance().getName() + ":" + id;
        getBukkitYml().set("worlds." + worldName + ".generator", string);
        saveBukkitYml();
        reloadBukkitYml();
    }
}
