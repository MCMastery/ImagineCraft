package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;

public class LocationCommand extends PlayerCommand {
    @Override
    public void onExecute(Player player, String label, String[] args) {
        //todo messageutils
        String location = player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ()
                + " &7in world &r" + player.getLocation().getWorld().getName();
        player.sendFormattedMessage("&7Your location: &f" + location);
    }

    @Override
    public String getDescription() {
        return "Displays your coordinates and world";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label;
    }

    @Override
    public String getLabel() {
        return "location";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"whereami", "pos", "position", "loc", "world"};
    }

    @Override
    public boolean allowExecution(Player player) {
        return true;
    }
}
