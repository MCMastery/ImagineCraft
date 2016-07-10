package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.utils.WorldUtils;

import java.util.Set;

public class WorldsCommand extends PlayerCommand {
    @Override
    public void onExecute(Player player, String label, String[] args) {
        //todo messageutils
        Set<String> worlds = WorldUtils.getOwnedWorlds(player.getUniqueId());
        if (worlds.size() == 0) {
            player.sendFormattedMessage("&cYou don't own any worlds!");
            return;
        }

        player.sendFormattedMessage("&a---------- &7Your Worlds &a----------");
        for (String world : worlds)
            player.sendFormattedMessage("&7- &f" + world);
    }

    @Override
    public String getDescription() {
        return "Displays a list of worlds you own";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label;
    }

    @Override
    public String getLabel() {
        return "worlds";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"myworlds"};
    }

    @Override
    public boolean allowExecution(Player player) {
        return true;
    }
}
