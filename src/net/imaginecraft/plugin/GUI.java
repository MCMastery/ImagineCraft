package net.imaginecraft.plugin;

import net.imaginecraft.plugin.listeners.internal.guiclick.GUIClickListener;
import net.imaginecraft.plugin.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class GUI implements GUIClickListener {
    private static Map<UUID, GUI> openedGUIs = new HashMap<>();

    public static GUI getGUI(Player player) {
        return openedGUIs.get(player.getUniqueId());
    }

    private final String title;
    private final Player player;
    private Inventory inventory;

    public GUI(Player player, int rows, String title) {
        this.title = StringUtils.format(title);
        this.inventory = Bukkit.createInventory(null, rows * 9, this.title);
        this.player = player;
    }

    public int getRows() {
        return this.inventory.getSize() / 9;
    }
    public String getTitle() {
        return this.title;
    }
    public Player getPlayer() {
        return this.player;
    }
    public Inventory getInventory() {
        return this.inventory;
    }

    public void open() {
        this.player.closeInventory();
        GUI previousGUI = getGUI(this.player);
        if (previousGUI != null)
            previousGUI.onClose();

        this.player.openGUI(this);
        openedGUIs.put(this.player.getUniqueId(), this);
    }
    public void onClose() {
        openedGUIs.remove(this.player.getUniqueId());
    }
}