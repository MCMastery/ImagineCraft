package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.utils.MessageUtils;

public class TeleportDenyCommand extends PlayerCommand {
    @Override
    public void onExecute(Player player, String label, String[] args) {
        if (player.getPendingRequest() == null)
            player.sendMessage(MessageUtils.getNoPendingRequestMessage());
        else {
            player.getPendingRequest().deny();
            player.sendMessage(MessageUtils.getTeleportRequestDeniedMessage());
        }
    }

    @Override
    public String getDescription() {
        return "Denies the pending teleport request";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label;
    }

    @Override
    public String getLabel() {
        return "tpdeny";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"tpno"};
    }

    @Override
    public boolean allowExecution(Player player) {
        return true;
    }
}
