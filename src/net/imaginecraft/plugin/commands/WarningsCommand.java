package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.Warning;
import net.imaginecraft.plugin.utils.MessageUtils;
import net.imaginecraft.plugin.utils.StringUtils;
import org.bukkit.command.CommandSender;

public class WarningsCommand extends Command {
    @Override
    public void onExecute(CommandSender sender, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(getUsageMessage(label));
            return;
        }

        Player player = Player.getOnlinePlayer(args[0]);
        if (player == null) {
            sender.sendMessage(MessageUtils.getUnknownPlayerMessage(args[0]));
            return;
        }

        //todo messageutils
        sender.sendMessage(StringUtils.format("&a---------- &7" + args[0] + "'s Warnings &a----------"));
        for (Warning warning : player.getWarnings())
            sender.sendMessage(StringUtils.format("&c" + warning.getReason() + " &ffrom: " + Player.getPlayerName(warning.getWarner())));
    }

    @Override
    public String getDescription() {
        return "Lists the warnings a player has received";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label + " <PlayerName>";
    }

    @Override
    public String getLabel() {
        return "warnings";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"warninglist", "listwarnings", "warns"};
    }

    @Override
    public boolean allowExecution(Player player) {
        return player.roleAtLeast(Player.Role.MODERATOR);
    }
}
