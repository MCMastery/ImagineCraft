package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.utils.MessageUtils;

public class TeleportAcceptCommand extends PlayerCommand {
    @Override
    public void onExecute(Player player, String label, String[] args) {
        if (player.getPendingRequest() == null)
            player.sendMessage(MessageUtils.getNoPendingRequestMessage());
        else {
            player.getPendingRequest().accept();
            player.sendMessage(MessageUtils.getTeleportRequestAcceptedMessage());
        }
    }

    @Override
    public String getDescription() {
        return "Accepts the pending teleport request";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label;
    }

    @Override
    public String getLabel() {
        return "tpaccept";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"tpyes"};
    }

    @Override
    public boolean allowExecution(Player player) {
        return true;
    }
}
