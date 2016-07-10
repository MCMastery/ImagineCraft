package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.utils.MessageUtils;
import net.imaginecraft.plugin.utils.StringUtils;

public class BalanceCommand extends PlayerCommand {
    @Override
    public void onExecute(Player player, String label, String[] args) {
        player.sendMessage(MessageUtils.getDisplayCoinsMessage(StringUtils.format(player.getCoins())));
    }

    @Override
    public String getDescription() {
        return "Displays how many coins you have";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label;
    }

    @Override
    public String getLabel() {
        return "balance";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"bal", "coins", "money"};
    }

    @Override
    public boolean allowExecution(Player player) {
        return true;
    }
}
