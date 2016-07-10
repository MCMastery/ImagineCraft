package net.imaginecraft.plugin.utils;

import net.imaginecraft.plugin.WorldData;
import net.imaginecraft.plugin.WorldGenerator;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.*;

public class WorldUtils {
    private WorldUtils() {}

    public static Set<String> getOwnedWorlds(UUID player) {
        Set<String> worlds = new HashSet<>();
        FileConfiguration worldData = FileUtils.getWorldData();
        for (String worldName : worldData.getKeys(false)) {
            String ownerId = worldData.getString(worldName + ".owner");
            if (ownerId == null) {
                LogUtils.error("Corrupt worlds.yml for world " + worldName + "!!!");
                continue;
            }

            UUID owner = UUID.fromString(worldData.getString(worldName + ".owner"));
            if (owner.equals(player))
                worlds.add(worldName);
        }
        return worlds;
    }

    public static WorldData loadWorldData(String worldName) {
        try {
            FileConfiguration worldDataFile = FileUtils.getWorldData();
            String ownerId = worldDataFile.getString(worldName + ".owner");
            String restrictionTypeName = worldDataFile.getString(worldName + ".restriction-type");
            String generatorName = worldDataFile.getString(worldName + ".generator");
            List<String> whitelist = worldDataFile.getStringList(worldName + ".whitelist");

            if (ownerId == null || restrictionTypeName == null || whitelist == null || generatorName == null) {
                LogUtils.error("Corrupt worlds.yml for world " + worldName + "!!!");
                return null;
            }

            UUID owner = UUID.fromString(ownerId);
            WorldData.RestrictionType restrictionType = WorldData.RestrictionType.valueOf(restrictionTypeName);
            WorldGenerator generator = WorldGenerator.valueOf(generatorName);

            Set<UUID> whitelistIds = new HashSet<>();
            for (String s : whitelist)
                whitelistIds.add(UUID.fromString(s));

            return new WorldData(worldName, owner, restrictionType, whitelistIds, generator);
        } catch (NullPointerException e) {
            return null;
        }
    }
    public static void saveWorldData(WorldData data) {
        FileConfiguration worldDataFile = FileUtils.getWorldData();
        worldDataFile.set(data.getWorldName() + ".owner", data.getOwner().toString());
        worldDataFile.set(data.getWorldName() + ".restriction-type", data.getRestrictionType().name());
        worldDataFile.set(data.getWorldName() + ".generator", data.getGenerator().name());

        List<String> whitelist = new ArrayList<>();
        for (UUID id : data.getWhitelist())
            whitelist.add(id.toString());
        worldDataFile.set(data.getWorldName() + ".whitelist", whitelist);
        FileUtils.saveWorldData();
    }

    private static void loadWorld(String name) {
        // yes, this does load the world if it already exists.
        WorldCreator wc = new WorldCreator(name);
        wc.createWorld();
    }

    public static void loadWorlds() {
        List<String> worlds = getWorlds();
        // don't load the worlds that Bukkit already loaded
        worlds.remove("world");
        worlds.remove("world_nether");
        worlds.remove("world_the_end");

        for (String world : worlds) {
            LogUtils.info("Loading world " + world + "...");
            loadWorld(world);
        }

        LogUtils.info("Done loading worlds.");
    }


    public static List<String> getWorlds() {
        List<String> worlds = new ArrayList<>();
        File root = FileUtils.getServerFolder();
        File[] rootFiles = root.listFiles();
        if (rootFiles != null) {
            for (File file : rootFiles) {
                // see if file is a world folder
                if (file.isDirectory()) {
                    String[] files = file.list();
                    if (files != null) {
                        List<String> fileList = Arrays.asList(files);
                        if (fileList.contains("data") && fileList.contains("playerdata") && fileList.contains("region") && fileList.contains("level.dat")
                                && fileList.contains("session.lock") && fileList.contains("uid.dat"))
                            worlds.add(file.getName());
                    }
                }
            }
        }

        return worlds;
    }
}
