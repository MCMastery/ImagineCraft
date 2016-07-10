package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.utils.MessageUtils;
import org.bukkit.inventory.ItemStack;

public class ClearCommand extends PlayerCommand {
    @Override
    public void onExecute(Player player, String label, String[] args) {
        player.getInventory().setArmorContents(new ItemStack[] {});
        player.getInventory().clear();
        player.sendMessage(MessageUtils.getInventoryClearedMessage());
    }

    @Override
    public String getDescription() {
        return "Clears your entire inventory";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label;
    }

    @Override
    public String getLabel() {
        return "clear";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"clearinventory", "ci"};
    }

    @Override
    public boolean allowExecution(Player player) {
        return true;
    }
}
