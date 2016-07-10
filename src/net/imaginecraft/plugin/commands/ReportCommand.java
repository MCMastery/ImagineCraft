package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;

public class ReportCommand extends PlayerCommand {
    @Override
    public void onExecute(Player player, String label, String[] args) {

    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getUsage(String label) {
        return null;
    }

    @Override
    public String getLabel() {
        return null;
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean allowExecution(Player player) {
        return false;
    }
}
