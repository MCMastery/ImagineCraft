package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.WorldData;
import net.imaginecraft.plugin.utils.LogUtils;
import net.imaginecraft.plugin.utils.MessageUtils;
import net.imaginecraft.plugin.utils.WorldUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldTeleportCommand extends PlayerCommand {
    @Override
    public void onExecute(Player player, String label, String[] args) {
        //todo messageutils
        if (args.length != 1) {
            player.sendMessage(getUsageMessage(label));
            return;
        }

        World world = Bukkit.getWorld(args[0]);
        if (world == null) {
            player.sendFormattedMessage("&cUnknown world (" + args[0] + ")!");
            return;
        }

        WorldData data = WorldUtils.loadWorldData(world.getName());
        if (data == null || !data.allowedToEnter(player.getUniqueId())) {
            if (data == null)
                LogUtils.error("Null WorldData for world " + world.getName() + "!");
            player.sendFormattedMessage("&cYou are not allowed to enter that world!");
            return;
        }

        player.teleport(world.getSpawnLocation());
        player.sendMessage(MessageUtils.getTeleportMessage(args[0]));
    }

    @Override
    public String getDescription() {
        return "Teleports you to a world";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label + " <WorldName>";
    }

    @Override
    public String getLabel() {
        return "worldteleport";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"worldtp", "wtp"};
    }

    @Override
    public boolean allowExecution(Player player) {
        return true;
    }
}
