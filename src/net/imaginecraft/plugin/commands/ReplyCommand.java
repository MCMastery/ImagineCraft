package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.utils.MessageUtils;
import net.imaginecraft.plugin.utils.StringUtils;

public class ReplyCommand extends PlayerCommand {
    @Override
    public void onExecute(Player player, String label, String[] args) {
        if (args.length < 1) {
            player.sendMessage(getUsageMessage(label));
            return;
        }

        if (!player.hasConversation() || !player.getConversation().isOnline()) {
            player.endConversation();
            player.sendMessage(MessageUtils.getNoConversationMessage());
            return;
        }

        String msg = StringUtils.join(args, " ", 0);
        player.messageToConversation(msg);
    }

    @Override
    public String getDescription() {
        return "Replies to a player with a message";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label + " <Message>";
    }

    @Override
    public String getLabel() {
        return "reply";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"r", "respond"};
    }

    @Override
    public boolean allowExecution(Player player) {
        return true;
    }
}
