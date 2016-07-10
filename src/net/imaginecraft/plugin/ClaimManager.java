package net.imaginecraft.plugin;

import net.imaginecraft.plugin.utils.LogUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClaimManager {
    private static final String CLAIMS_PATH = "claims";
    private static Set<Claim> loadedClaims = new HashSet<>();
    private static FileConfiguration dataFile = null;
    private static File dataFileSource = null;

    private ClaimManager() {}

    public static void reloadCustomConfig() {
        String path = "claims.yml";
        if (dataFileSource == null)
            dataFileSource = new File(ImagineCraft.getInstance().getDataFolder(), path);
        dataFile = YamlConfiguration.loadConfiguration(dataFileSource);
    }
    public static FileConfiguration getDataFile() {
        if (dataFile == null)
            reloadCustomConfig();
        return dataFile;
    }
    public static void saveDataFile() {
        if (dataFile == null || dataFileSource == null)
            return;
        try {
            getDataFile().save(dataFileSource);
        } catch (IOException e) {
            LogUtils.error("IOException when attempting to save claim data file!");
            e.printStackTrace();
        }
    }


    public static void loadClaims() {
        loadedClaims = new HashSet<>();
        List<String> claims = getDataFile().getStringList(CLAIMS_PATH);
        for (String claim : claims)
            loadedClaims.add(Claim.deserialize(claim));
    }

    public static Set<Claim> getLoadedClaims() {
        return loadedClaims;
    }
    public static Claim getClaimContaining(Location location) {
        for (Claim claim : loadedClaims)
            if (claim.contains(location))
                return claim;
        return null;
    }
    public static Claim getClaimIntersecting(World world, int minX, int minZ, int maxX, int maxZ) {
        for (Claim claim : loadedClaims)
            if (claim.intersects(world, minX, minZ, maxX, maxZ))
                return claim;
        return null;
    }
    public static Claim getClaimIntersecting(Location pointOne, Location pointTwo) {
        for (Claim claim : loadedClaims)
            if (claim.intersects(pointOne, pointTwo))
                return claim;
        return null;
    }
    private static void saveClaims() {
        List<String> serializedClaims = new ArrayList<>();
        for (Claim claim : loadedClaims)
            serializedClaims.add(claim.serialize());
        getDataFile().set(CLAIMS_PATH, serializedClaims);
        saveDataFile();
    }
    public static void deleteClaim(Claim claim) {
        loadedClaims.remove(claim);
        saveClaims();
    }
    public static void saveClaim(Claim claim) {
        loadedClaims.add(claim);
        saveClaims();
    }
}
