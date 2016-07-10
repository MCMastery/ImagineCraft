package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.GUI;
import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.WorldData;
import net.imaginecraft.plugin.listeners.internal.guiclick.GUIClickEvent;
import net.imaginecraft.plugin.utils.LogUtils;
import net.imaginecraft.plugin.utils.StringUtils;
import net.imaginecraft.plugin.utils.WorldUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WorldPanelCommand extends PlayerCommand {
    public static GUI getGUI(Player player) {
        GUI gui = new GUI(player, 4, "&9World&6Panel") {
            @Override
            public void onGUIClick(GUIClickEvent evt) {
                LogUtils.info(evt.getSlot());
            }
        };
        for (String world : WorldUtils.getOwnedWorlds(player.getUniqueId())) {
            WorldData data = WorldUtils.loadWorldData(world);
            if (data == null) {
                LogUtils.error("No/corrupt WorldData for world " + world + "!");
                continue;
            }
            ItemStack item = data.getGenerator().getItemRepresentation().clone();
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(StringUtils.format("&9" + world));
            List<String> lore = new ArrayList<>();
            lore.add(StringUtils.format("&7Type: &f" + data.getGenerator().getName()));
            lore.add(StringUtils.format("&7Restriction: &f" + data.getRestrictionType().getName()));
            meta.setLore(lore);
            item.setItemMeta(meta);
            gui.getInventory().addItem(item);
        }
        return gui;
    }

    @Override
    public void onExecute(Player player, String label, String[] args) {
        GUI gui = getGUI(player);
        gui.open();
    }

    @Override
    public String getDescription() {
        return "Displays the world control panel";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label;
    }

    @Override
    public String getLabel() {
        return "worldpanel";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"controlpanel", "wpanel", "wp", "cp"};
    }

    @Override
    public boolean allowExecution(Player player) {
        return true;
    }
}
