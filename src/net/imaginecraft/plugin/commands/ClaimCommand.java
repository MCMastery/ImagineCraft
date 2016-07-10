package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Claim;
import net.imaginecraft.plugin.ClaimManager;
import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.utils.MessageUtils;
import net.imaginecraft.plugin.utils.StringUtils;

import java.math.BigInteger;

public class ClaimCommand extends PlayerCommand {
    @Override
    public void onExecute(Player player, String label, String[] args) {
        //todo messageutils
        if (player.getSelectionPointOne()  == null || player.getSelectionPointTwo() == null) {
            player.sendMessage(MessageUtils.getNoClaimSelectionMessage());
            return;
        }
        if (!player.getSelectionPointOne().getWorld().getName().equals(player.getSelectionPointTwo().getWorld().getName())) {
            player.sendMessage(MessageUtils.getDifferentWorldsMessage());
            return;
        }
        Claim intersectingClaim = ClaimManager.getClaimIntersecting(player.getSelectionPointOne(), player.getSelectionPointTwo());
        if (intersectingClaim != null) {
            player.sendFormattedMessage("&c" + Player.getPlayerName(intersectingClaim.getOwner()) + " owns part of your selection!");
            return;
        }

        BigInteger price = Claim.getClaimPrice(player.getSelectionPointOne().getBlockX(), player.getSelectionPointOne().getBlockZ(),
                player.getSelectionPointTwo().getBlockX(), player.getSelectionPointTwo().getBlockZ());
        if (!player.hasEnoughCoins(price)) {
            player.sendFormattedMessage("&cYou need " + StringUtils.format(price) + " coins to buy that claim!");
            return;
        }


        player.claimSelection();
        player.sendMessage(MessageUtils.getClaimMessage());
    }

    @Override
    public String getDescription() {
        return "Claims your selection";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label;
    }

    @Override
    public String getLabel() {
        return "claim";
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
