package net.imaginecraft.plugin.commands.overridden;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.commands.Command;
import net.imaginecraft.plugin.utils.MessageUtils;
import org.bukkit.command.CommandSender;

public class OverriddenMeCommand extends Command {
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
        return "me";
    }
    @Override
    public String[] getAliases() {
        return new String[] {};
    }
    @Override
    public void onExecute(CommandSender sender, String label, String[] args) {
        sender.sendMessage(MessageUtils.getUnknownCommandMessage(label));
    }
    @Override
    public boolean allowExecution(Player player) {
        return true;
    }
}
