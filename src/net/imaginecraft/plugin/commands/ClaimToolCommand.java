package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.utils.ConfigUtils;
import net.imaginecraft.plugin.utils.MessageUtils;
import net.imaginecraft.plugin.utils.StringUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class ClaimToolCommand extends PlayerCommand {
    @Override
    public void onExecute(Player player, String label, String[] args) {
        ItemStack item = new ItemStack(ConfigUtils.getLandClaimTool());
        ItemMeta meta = item.getItemMeta();
        meta.setLore(new ArrayList<>(Arrays.asList(ConfigUtils.getLandClaimToolLore(), StringUtils.format("&7Left-click a block to select pos. 1"),
                StringUtils.format("&7Right-click a block to select pos. 2"))));
        meta.setDisplayName(ConfigUtils.getLandClaimToolLore());
        item.setItemMeta(meta);
        player.give(item);

        player.sendMessage(MessageUtils.getReceiveClaimToolMessage());
    }

    @Override
    public String getDescription() {
        return "Gives you the land claim tool";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label;
    }

    @Override
    public String getLabel() {
        return "claimtool";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"landclaimtool", "landclaimer", "claimer"};
    }

    @Override
    public boolean allowExecution(Player player) {
        return true;
    }
}
