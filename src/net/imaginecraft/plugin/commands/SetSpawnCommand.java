package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.utils.ConfigUtils;
import net.imaginecraft.plugin.utils.MessageUtils;

public class SetSpawnCommand extends PlayerCommand {
    @Override
    public void onExecute(Player player, String label, String[] args) {
        player.getLocation().getWorld().setSpawnLocation(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
        ConfigUtils.setSpawn(player.getLocation());
        player.sendMessage(MessageUtils.getSetSpawnMessage());
    }

    @Override
    public String getDescription() {
        return "Sets the spawn to your current location";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label;
    }

    @Override
    public String getLabel() {
        return "setspawn";
    }

    @Override
    public String[] getAliases() {
        return new String[] {};
    }

    @Override
    public boolean allowExecution(Player player) {
        return player.roleAtLeast(Player.Role.OWNER);
    }
}
