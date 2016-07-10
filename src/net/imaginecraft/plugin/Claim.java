package net.imaginecraft.plugin;

import net.imaginecraft.plugin.utils.ConfigUtils;
import net.imaginecraft.plugin.utils.LogUtils;
import org.bukkit.Location;
import org.bukkit.World;

import java.math.BigInteger;
import java.util.UUID;

public class Claim {
    private final UUID owner;
    private final String worldName;
    private final int minX, minZ, maxX, maxZ;

    public Claim(Player owner, Location pointOne, Location pointTwo) {
        if (!pointOne.getWorld().getName().equals(pointTwo.getWorld().getName()))
            LogUtils.error("Selection points for a claim differ in worlds!");
        this.owner = owner.getUniqueId();
        this.worldName = pointOne.getWorld().getName();
        this.minX = Math.min(pointOne.getBlockX(), pointTwo.getBlockX());
        this.minZ = Math.min(pointOne.getBlockZ(), pointTwo.getBlockZ());
        this.maxX = Math.max(pointOne.getBlockX(), pointTwo.getBlockX());
        this.maxZ = Math.max(pointOne.getBlockZ(), pointTwo.getBlockZ());
    }
    public Claim(UUID owner, String worldName, int minX, int minZ, int maxX, int maxZ) {
        this.owner = owner;
        this.worldName = worldName;
        this.minX = minX;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxZ = maxZ;
    }

    public UUID getOwner() {
        return this.owner;
    }
    public String getWorldName() {
        return this.worldName;
    }
    public int getMinX() {
        return this.minX;
    }
    public int getMinZ() {
        return this.minZ;
    }
    public int getMaxX() {
        return this.maxX;
    }
    public int getMaxZ() {
        return this.maxZ;
    }

    public boolean contains(Location location) {
        return location.getWorld().getName().equals(this.worldName) && location.getX() >= this.minX && location.getX() <= this.maxX
                && location.getZ() >= this.minZ && location.getZ() <= this.maxZ;
    }
    public boolean intersects(World world, double minX, double minZ, double maxX, double maxZ) {
        return world.getName().equals(this.worldName) && this.minX <= maxX && this.maxX >= minX
                && this.minZ <= maxZ && this.maxZ >= minZ;
    }

    public boolean intersects(Location pointOne, Location pointTwo) {
        if (!pointOne.getWorld().getName().equals(pointTwo.getWorld().getName())) {
            LogUtils.error("Selection points for a claim differ in worlds!");
            return false;
        }
        int minX = Math.min(pointOne.getBlockX(), pointTwo.getBlockX());
        int minZ = Math.min(pointOne.getBlockZ(), pointTwo.getBlockZ());
        int maxX = Math.max(pointOne.getBlockX(), pointTwo.getBlockX());
        int maxZ = Math.max(pointOne.getBlockZ(), pointTwo.getBlockZ());
        return intersects(pointOne.getWorld(), minX, minZ, maxX, maxZ);
    }

    public String serialize() {
        return this.owner + "," + this.worldName + "," + this.minX + "," + this.minZ + "," + this.maxX + "," + this.maxZ;
    }
    public static Claim deserialize(String s) {
        String[] split = s.split(",");
        if (split.length != 6) {
            LogUtils.error("Attempting to deserialize invalid claim (" + s + ")!");
            return null;
        }
        UUID owner = UUID.fromString(split[0]);
        String worldName = split[1];
        try {
            int minX = Integer.parseInt(split[2]);
            int minZ = Integer.parseInt(split[3]);
            int maxX = Integer.parseInt(split[4]);
            int maxZ = Integer.parseInt(split[5]);
            return new Claim(owner, worldName, minX, minZ, maxX, maxZ);
        } catch (NumberFormatException e) {
            LogUtils.error("Attempting to deserialize invalid claim (" + s + ")!");
            return null;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Claim && ((Claim) other).getOwner().equals(this.owner) && ((Claim) other).getWorldName().equals(this.worldName)
                && ((Claim) other).getMinX() == this.minX && ((Claim) other).getMinZ() == this.minZ
                && ((Claim) other).getMaxX() == this.maxX && ((Claim) other).getMaxZ() == this.maxZ;
    }



    public static BigInteger getClaimPrice(int x1, int z1, int x2, int z2) {
        int width = Math.abs(x2 - x1) + 1, height = Math.abs(z2 - z1) + 1; // look at selection as 2d rectangle
        long area = width * height; // how many blocks in selection
        return BigInteger.valueOf(area).multiply(ConfigUtils.getClaimBlockPrice());
    }
}
