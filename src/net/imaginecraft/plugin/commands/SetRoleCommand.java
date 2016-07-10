package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.utils.MessageUtils;
import org.bukkit.command.CommandSender;

public class SetRoleCommand extends Command {
    @Override
    public String getDescription() {
        return "Sets the role of another player";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label + " <PlayerName> <Role>";
    }

    @Override
    public String getLabel() {
        return "setrole";
    }

    @Override
    public String[] getAliases() {
        return new String[] {};
    }

    @Override
    public void onExecute(CommandSender sender, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(getUsageMessage(label));
            return;
        }
        Player player = Player.getOnlinePlayer(args[0]);
        if (player == null) {
            sender.sendMessage(MessageUtils.getUnknownPlayerMessage(args[0]));
            return;
        }
        try {
            Player.Role role = Player.Role.valueOf(args[1].toUpperCase());
            player.setRole(role);
            sender.sendMessage(MessageUtils.getUpdatedRoleMessage(player));
        } catch (IllegalArgumentException e) {
            sender.sendMessage(MessageUtils.getUnknownRoleMessage(args[1]));
        }
    }

    @Override
    public boolean allowExecution(Player player) {
        return player.roleAtLeast(Player.Role.OWNER);
    }
}
