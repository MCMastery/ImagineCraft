package net.imaginecraft.plugin;

import net.imaginecraft.plugin.utils.ConfigUtils;
import net.imaginecraft.plugin.utils.LogUtils;
import net.imaginecraft.plugin.utils.StringUtils;
import net.imaginecraft.plugin.utils.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class Player {
    private static Set<Player> loadedPlayers = new HashSet<>();

    // server role (in order of privileges)
    public enum Role {
        PLAYER("&7[Player]&r"),
        MODERATOR("&7[&2Moderator&7]&r"),
        OWNER("&7[&4&lOwner&7]&r");

        private String tag;

        Role(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return this.tag;
        }
        public String getFormattedTag() {
            return StringUtils.format(this.tag);
        }
    }
    // donor rank
    public enum Rank {
        NONE("");

        private String tag;

        Rank(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return this.tag;
        }
        public String getFormattedTag() {
            return StringUtils.format(this.tag);
        }
    }

    private org.bukkit.entity.Player player;
    private TeleportRequest pendingRequest;
    private Location selectionPointOne, selectionPointTwo;
    private Teleport pendingTeleport;
    private Player conversation;
    private FileConfiguration dataFile;
    private File dataFileSource;

    private Player(org.bukkit.entity.Player player) {
        this.player = player;
        this.pendingTeleport = null;
        this.dataFile = null;
        this.pendingRequest = null;
        this.conversation = null;
        this.selectionPointOne = null;
        this.selectionPointTwo = null;
        for (Player p : loadedPlayers)
            if (p.getUniqueId().equals(player.getUniqueId()))
                loadedPlayers.remove(p);
        loadedPlayers.add(this);
    }


    public static Set<Player> getLoadedPlayers() {
        return loadedPlayers;
    }
    public static void unload(Player player) {
        for (Player p : new HashSet<>(Player.getLoadedPlayers()))
            if (player.getUniqueId().equals(p.getUniqueId()))
                Player.getLoadedPlayers().remove(player);
    }
    public static Player getPlayer(org.bukkit.entity.Player player) {
        for (Player p : loadedPlayers)
            if (p.getUniqueId().equals(player.getUniqueId()))
                return p;
        return new Player(player);
    }
    public static Player getOnlinePlayer(String name) {
        for (org.bukkit.entity.Player player : Bukkit.getOnlinePlayers())
            if (player.getName().equalsIgnoreCase(name))
                return getPlayer(player);
        return null;
    }
    public static Set<Player> getOnlinePlayers() {
        Set<Player> players = new HashSet<>();
        for (org.bukkit.entity.Player player : Bukkit.getOnlinePlayers())
            players.add(new Player(player));
        return players;
    }
    public static UUID getOfflinePlayer(String name) {
        try {
            return UUIDFetcher.getUUIDOf(name);
        } catch (Exception e) {
            LogUtils.warning("Exception while attempting to get an offline player's unique id!");
            e.printStackTrace();
            return null;
        }
    }
    public static String getPlayerName(UUID id) {
        return Bukkit.getOfflinePlayer(id).getName();
    }



    public void reloadDataFile() {
        String path = "players/" + this.player.getUniqueId() + ".yml";
        if (this.dataFileSource == null)
            this.dataFileSource = new File(ImagineCraft.getInstance().getDataFolder(), path);
        this.dataFile = YamlConfiguration.loadConfiguration(this.dataFileSource);
    }
    public FileConfiguration getDataFile() {
        if (this.dataFile == null)
            reloadDataFile();
        return this.dataFile;
    }
    public void saveDataFile() {
        if (this.dataFile == null || this.dataFileSource == null)
            return;
        try {
            getDataFile().save(this.dataFileSource);
        } catch (IOException e) {
            LogUtils.error("IOException when attempting to save player data file!");
            e.printStackTrace();
        }
    }


    public Role getRole() {
        // if the role key does not exist, create it
        if (!getDataFile().getKeys(false).contains("role"))
            setRole(Role.PLAYER);
        Role role;
        try {
            role = Role.valueOf(getDataFile().getString("role"));
        } catch (IllegalArgumentException e) {
            LogUtils.error("Player has invalid role! Resetting player's role to PLAYER");
            setRole(role = Role.PLAYER);
        }
        return role;
    }
    public void setRole(Role role) {
        getDataFile().set("role", role.name());
        saveDataFile();
        reloadDataFile();
    }
    public boolean roleAtLeast(Role role) {
        return getRole().ordinal() >= role.ordinal();
    }

    public Rank getRank() {
        // if the rank key does not exist, create it
        if (!getDataFile().getKeys(false).contains("rank"))
            setRank(Rank.NONE);
        Rank rank;
        try {
            rank = Rank.valueOf(getDataFile().getString("rank"));
        } catch (IllegalArgumentException e) {
            LogUtils.error("Player has invalid rank! Resetting player's rank to NONE");
            setRank(rank = Rank.NONE);
        }
        return rank;
    }
    public void setRank(Rank rank) {
        getDataFile().set("rank", rank.name());
        saveDataFile();
        reloadDataFile();
    }
    public boolean rankAtLeast(Rank rank) {
        return getRank().ordinal() >= rank.ordinal();
    }


    public BigInteger getCoins() {
        // if the coins key does not exist, create it
        if (!getDataFile().getKeys(false).contains("coins"))
            setCoins(BigInteger.ZERO);
        try {
            return new BigInteger(getDataFile().getString("coins"));
        } catch (NumberFormatException e) {
            LogUtils.error("Player " + getName() + " has invalid coin amount!");
            return BigInteger.ZERO;
        }
    }
    public void setCoins(BigInteger coins) {
        getDataFile().set("coins", coins.toString());
        saveDataFile();
        reloadDataFile();
    }
    public void giveCoins(BigInteger coins) {
        setCoins(getCoins().add(coins));
    }
    public void takeCoins(BigInteger coins) {
        setCoins(getCoins().subtract(coins));
    }
    public boolean hasEnoughCoins(BigInteger needed) {
        return getCoins().compareTo(needed) >= 0;
    }


    public Set<Warning> getWarnings() {
        Set<Warning> warnings = new HashSet<>();
        List<String> warningsDeserialized = getDataFile().getStringList("warnings");
        if (warningsDeserialized == null)
            return warnings;
        for (String s : warningsDeserialized)
            warnings.add(Warning.deserialize(s));
        return warnings;
    }
    public void setWarnings(Set<Warning> warnings) {
        List<String> warningsDeserialized = new ArrayList<>();
        for (Warning warning : warnings)
            warningsDeserialized.add(warning.serialize());
        getDataFile().set("warnings", warningsDeserialized);
        saveDataFile();
    }
    public void addWarning(Warning warning) {
        Set<Warning> warnings = getWarnings();
        warnings.add(warning);
        setWarnings(warnings);
    }


    public Location getSelectionPointOne() {
        return this.selectionPointOne;
    }
    public void setSelectionPointOne(Location selectionPointOne) {
        this.selectionPointOne = selectionPointOne;
    }
    public Location getSelectionPointTwo() {
        return this.selectionPointTwo;
    }
    public void setSelectionPointTwo(Location selectionPointTwo) {
        this.selectionPointTwo = selectionPointTwo;
    }
    public void claimSelection() {
        Claim claim = new Claim(this, this.selectionPointOne, this.selectionPointTwo);
        ClaimManager.saveClaim(claim);
    }



    public String getName() {
        return this.player.getName();
    }
    public UUID getUniqueId() {
        return this.player.getUniqueId();
    }

    public Location getLocation() {
        return this.player.getLocation();
    }
    public void setLocation(Location location) {
        this.player.teleport(location);
    }


    public Teleport getPendingTeleport() {
        return this.pendingTeleport;
    }
    public boolean hasPendingTeleport() {
        return this.pendingTeleport != null;
    }
    public void teleport(Location location) {
        if (hasPendingTeleport())
            this.pendingTeleport.cancel();
        Teleport teleport = new Teleport(this) {
            @Override
            public Location getDestination() {
                return location;
            }
        };
        teleport.start();
        this.pendingTeleport = teleport;
    }
    // if player destination moves, then it will teleport to where they moved to
    public void teleport(Player player) {
        if (hasPendingTeleport())
            this.pendingTeleport.cancel();
        Teleport teleport = new Teleport(this) {
            @Override
            public Location getDestination() {
                return player.getLocation();
            }
        };
        teleport.start();
        this.pendingTeleport = teleport;
    }

    public void requestTeleport(Player player) {
        if (this.pendingRequest != null)
            this.pendingRequest.cancel();
        TeleportRequest request = new TeleportRequest(this, player);
        request.request();
    }


    public TeleportRequest getPendingRequest() {
        return this.pendingRequest;
    }
    public void setPendingRequest(TeleportRequest request) {
        this.pendingRequest = request;
    }



    public Player getConversation() {
        return this.conversation;
    }
    public void setConversation(Player player) {
        this.conversation = player;
    }
    public void endConversation() {
        this.conversation = null;
    }
    public boolean hasConversation() {
        return this.conversation != null;
    }


    public void messageToConversation(String msg) {
        sendMessage(ConfigUtils.formatMessage(msg, "You", this.conversation.getName()));
        this.conversation.sendMessage(ConfigUtils.formatMessage(msg, getName(), "You"));
    }

    public boolean hasPlayedBefore() {
        return this.player.hasPlayedBefore();
    }
    public boolean isOnline() {
        return this.player.isOnline();
    }

    public void openGUI(GUI gui) {
        this.player.openInventory(gui.getInventory());
    }

    public void closeInventory() {
        this.player.closeInventory();
    }

    public void sendMessage(String msg) {
        this.player.sendMessage(msg);
    }
    public void sendFormattedMessage(String msg) {
        sendMessage(StringUtils.format(msg));
    }

    public void playSound(Sound sound) {
        this.player.playSound(this.player.getLocation(), sound, 0.5f, 0.5f);
    }

    public void kick(String reason) {
        this.player.kickPlayer(reason);
        // this is not picked up by PlayerUnloader (PlayerQuitEvent)
        unload(this);
    }
    public void kickFormatted(String reason) {
        kick(StringUtils.format(reason));
    }

    public void give(ItemStack... items) {
        this.player.getInventory().addItem(items);
    }
    public PlayerInventory getInventory() {
        return this.player.getInventory();
    }
}
