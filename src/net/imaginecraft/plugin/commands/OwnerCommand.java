package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Claim;
import net.imaginecraft.plugin.ClaimManager;
import net.imaginecraft.plugin.Player;

public class OwnerCommand extends PlayerCommand {
    @Override
    public void onExecute(Player player, String label, String[] args) {
        Claim claim = ClaimManager.getClaimContaining(player.getLocation());
        if (claim == null)
            player.sendFormattedMessage("&aNo one owns the land you are standing on.");
        else
            player.sendFormattedMessage("&c" + Player.getPlayerName(claim.getOwner()) + " owns the land you are standing on.");
    }

    @Override
    public String getDescription() {
        return "Displays who owns the land your are located at";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label;
    }

    @Override
    public String getLabel() {
        return "owner";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"whoowns", "landinfo", "claiminfo", "info"};
    }

    @Override
    public boolean allowExecution(Player player) {
        return true;
    }
}
