package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.utils.MessageUtils;
import org.bukkit.command.CommandSender;

// a command which can only be executed by a player
public abstract class PlayerCommand extends Command {
    public abstract void onExecute(Player player, String label, String[] args);

    @Override
    public final void onExecute(CommandSender sender, String label, String[] args) {
        if (sender instanceof org.bukkit.entity.Player) {
            Player player = Player.getPlayer((org.bukkit.entity.Player) sender);
            onExecute(player, label, args);
        } else {
            // if the console attempts to use this command, send them a message
            sender.sendMessage(MessageUtils.getPlayerOnlyCommandMessage());
        }
    }
}
