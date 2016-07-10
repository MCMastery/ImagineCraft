package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.utils.MessageUtils;

public class TeleportCommand extends PlayerCommand {
    @Override
    public void onExecute(Player player, String label, String[] args) {
        if (args.length != 1) {
            player.sendMessage(getUsageMessage(label));
            return;
        }

        Player destination = Player.getOnlinePlayer(args[0]);
        if (destination == null) {
            player.sendMessage(MessageUtils.getUnknownPlayerMessage(args[0]));
            return;
        }

        if (destination.getName().equals(player.getName())) {
            player.sendMessage(MessageUtils.getTeleportToSelfMessage());
            return;
        }

        player.requestTeleport(destination);
    }

    @Override
    public String getDescription() {
        return "Requests a teleport to another player";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label + " <PlayerName>";
    }

    @Override
    public String getLabel() {
        return "teleport";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"tp", "tpa"};
    }

    @Override
    public boolean allowExecution(Player player) {
        return true;
    }
}
