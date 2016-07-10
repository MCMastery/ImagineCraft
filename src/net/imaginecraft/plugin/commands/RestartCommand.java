package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.ImagineCraft;
import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class RestartCommand extends Command {
    @Override
    public void onExecute(CommandSender sender, String label, String[] args) {
        //todo messageutils
        for (Player p : Player.getOnlinePlayers())
            p.sendFormattedMessage("&c&lServer restarting in 30 seconds!");
        Bukkit.getScheduler().scheduleSyncDelayedTask(ImagineCraft.getInstance(), ImagineCraft::restartServer, 20 * 30); // give 30 second warning
    }

    @Override
    public String getDescription() {
        return "Restarts the server";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label;
    }

    @Override
    public String getLabel() {
        return "restart";
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
