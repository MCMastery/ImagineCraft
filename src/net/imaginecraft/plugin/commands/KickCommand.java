package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.utils.MessageUtils;
import net.imaginecraft.plugin.utils.StringUtils;

public class KickCommand extends PlayerCommand {
    @Override
    public void onExecute(Player player, String label, String[] args) {
        if (args.length < 2) {
            player.sendMessage(getUsageMessage(label));
            return;
        }

        Player kicked = Player.getOnlinePlayer(args[0]);
        if (kicked == null) {
            player.sendMessage(MessageUtils.getUnknownPlayerMessage(args[0]));
            return;
        }

        String reason = StringUtils.join(args, " ", 1);
        kicked.kick(reason);

        for (Player p : Player.getOnlinePlayers())
            p.sendFormattedMessage("&c" + player.getName() + " kicked " + kicked.getName() + "! Reason: &f" + reason);
    }

    @Override
    public String getDescription() {
        return "Kicks a player";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label + " <PlayerName> <Reason>";
    }

    @Override
    public String getLabel() {
        return "kick";
    }

    @Override
    public String[] getAliases() {
        return new String[] {};
    }

    @Override
    public boolean allowExecution(Player player) {
        return player.roleAtLeast(Player.Role.MODERATOR);
    }
}
