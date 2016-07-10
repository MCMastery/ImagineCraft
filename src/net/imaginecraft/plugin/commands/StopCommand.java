package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class StopCommand extends Command {
    @Override
    public void onExecute(CommandSender sender, String label, String[] args) {
        Bukkit.shutdown();
    }

    @Override
    public String getDescription() {
        return "Stops the server,with no warning";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label;
    }

    @Override
    public String getLabel() {
        return "stop";
    }

    @Override
    public String[] getAliases() {
        return new String[] {};
    }

    @Override
    public boolean allowExecution(Player player) {
        return player.roleAtLeast(Player.Role.OWNER);
    }
}
