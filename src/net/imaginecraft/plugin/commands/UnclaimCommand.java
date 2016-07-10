package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Claim;
import net.imaginecraft.plugin.ClaimManager;
import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.utils.MessageUtils;

public class UnclaimCommand extends PlayerCommand {
    @Override
    public void onExecute(Player player, String label, String[] args) {
        Claim claim = ClaimManager.getClaimContaining(player.getLocation());
        if (claim == null || !claim.getOwner().equals(player.getUniqueId())) {
            player.sendMessage(MessageUtils.getLandNotOwnedMessage());
            return;
        }
        ClaimManager.deleteClaim(claim);
        player.sendMessage(MessageUtils.getUnclaimedLandMessage());
    }

    @Override
    public String getDescription() {
        return "Unclaims the land you are standing on";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label;
    }

    @Override
    public String getLabel() {
        return "unclaim";
    }

    @Override
    public String[] getAliases() {
        return new String[] {};
    }

    @Override
    public boolean allowExecution(Player player) {
        return true;
    }
}
