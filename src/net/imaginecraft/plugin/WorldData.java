package net.imaginecraft.plugin;

import java.util.Set;
import java.util.UUID;

public class WorldData {
    public enum RestrictionType {
        WHITELIST("Whitelist"),
        PUBLIC("Public");

        public static final RestrictionType DEFAULT = WHITELIST;

        private final String name;

        RestrictionType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    private final String worldName;
    private final WorldGenerator generator;
    private UUID owner;
    private RestrictionType restrictionType;
    private Set<UUID> whitelist;

    public WorldData(String worldName, UUID owner, RestrictionType restrictionType, Set<UUID> whitelist, WorldGenerator generator) {
        this.worldName = worldName;
        this.owner = owner;
        this.restrictionType = restrictionType;
        this.whitelist = whitelist;
        this.generator = generator;
    }

    public String getWorldName() {
        return this.worldName;
    }
    public WorldGenerator getGenerator() {
        return this.generator;
    }
    public UUID getOwner() {
        return this.owner;
    }
    public void setOwner(UUID owner) {
        this.owner = owner;
    }
    public RestrictionType getRestrictionType() {
        return this.restrictionType;
    }
    public void setRestrictionType(RestrictionType restrictionType) {
        this.restrictionType = restrictionType;
    }


    public Set<UUID> getWhitelist() {
        return this.whitelist;
    }
    public void setWhitelist(Set<UUID> whitelist) {
        this.whitelist = whitelist;
    }
    public void addToWhitelist(UUID player) {
        this.whitelist.add(player);
    }
    public void removeFromWhitelist(UUID player) {
        this.whitelist.remove(player);
    }

    public boolean allowedToEnter(UUID player) {
        return this.restrictionType == RestrictionType.PUBLIC || this.owner.equals(player) || this.whitelist.contains(player);
    }
}
