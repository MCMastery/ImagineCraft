package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.utils.ConfigUtils;
import net.imaginecraft.plugin.utils.MessageUtils;

public class SpawnCommand extends PlayerCommand {
    @Override
    public void onExecute(Player player, String label, String[] args) {
        player.sendMessage(MessageUtils.getTeleportMessage("spawn"));
        player.teleport(ConfigUtils.getSpawn());
    }

    @Override
    public String getDescription() {
        return "Teleports you to spawn";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label;
    }

    @Override
    public String getLabel() {
        return "spawn";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"hub"};
    }

    @Override
    public boolean allowExecution(Player player) {
        return true;
    }
}
