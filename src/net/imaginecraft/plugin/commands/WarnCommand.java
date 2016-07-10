package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.utils.MessageUtils;
import net.imaginecraft.plugin.utils.PunishUtils;
import net.imaginecraft.plugin.utils.StringUtils;

public class WarnCommand extends PlayerCommand {
    @Override
    public void onExecute(Player sender, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(getUsageMessage(label));
            return;
        }

        Player player = Player.getOnlinePlayer(args[0]);
        if (player == null) {
            sender.sendMessage(MessageUtils.getUnknownPlayerMessage(args[0]));
            return;
        }

        String reason = StringUtils.join(args, " ", 1);
        PunishUtils.warn(sender, player, reason);
        sender.sendFormattedMessage("&cYou have warned " + player.getName() + ". Reason: " + reason);
    }

    @Override
    public String getDescription() {
        return "Warns another player";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label + " <PlayerName> <Reason>";
    }

    @Override
    public String getLabel() {
        return "warn";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"warning"};
    }

    @Override
    public boolean allowExecution(Player player) {
        return player.roleAtLeast(Player.Role.MODERATOR);
    }
}
