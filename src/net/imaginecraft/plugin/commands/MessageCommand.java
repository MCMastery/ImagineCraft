package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.utils.MessageUtils;
import net.imaginecraft.plugin.utils.StringUtils;

public class MessageCommand extends PlayerCommand {
    @Override
    public void onExecute(Player player, String label, String[] args) {
        if (args.length < 2) {
            player.sendMessage(getUsageMessage(label));
            return;
        }

        Player recipient = Player.getOnlinePlayer(args[0]);
        if (recipient == null) {
            player.sendMessage(MessageUtils.getUnknownPlayerMessage(args[0]));
            return;
        }

        String msg = StringUtils.join(args, " ", 1);
        player.setConversation(recipient);
        recipient.setConversation(player);
        player.messageToConversation(msg);
    }

    @Override
    public String getDescription() {
        return "Messages another player";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label + " <PlayerName> <Message>";
    }

    @Override
    public String getLabel() {
        return "msg";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"message", "tell", "m"};
    }

    @Override
    public boolean allowExecution(Player player) {
        return true;
    }
}
