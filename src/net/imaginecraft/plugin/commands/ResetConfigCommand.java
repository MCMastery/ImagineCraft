package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.utils.ConfigUtils;
import net.imaginecraft.plugin.utils.MessageUtils;
import org.bukkit.command.CommandSender;

public class ResetConfigCommand extends Command {
    @Override
    public void onExecute(CommandSender sender, String label, String[] args) {
        ConfigUtils.resetConfig();
        sender.sendMessage(MessageUtils.getResetConfigMessage());
    }

    @Override
    public boolean allowExecution(Player player) {
        return player.roleAtLeast(Player.Role.OWNER);
    }

    @Override
    public String getDescription() {
        return "Resets the config.yml file";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label;
    }

    @Override
    public String getLabel() {
        return "resetcfg";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"resetconfig"};
    }
}
