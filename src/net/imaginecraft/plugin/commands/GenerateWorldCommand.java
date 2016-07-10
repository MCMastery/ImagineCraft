package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.WorldData;
import net.imaginecraft.plugin.WorldGenerator;
import net.imaginecraft.plugin.utils.FileUtils;
import net.imaginecraft.plugin.utils.MessageUtils;
import net.imaginecraft.plugin.utils.StringUtils;
import net.imaginecraft.plugin.utils.WorldUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.HashSet;

public class GenerateWorldCommand extends Command {
    @Override
    public void onExecute(CommandSender sender, String label, String[] args) {
        //todo messageutils
        if (args.length != 3) {
            sender.sendMessage(getUsageMessage(label));
            return;
        }

        String type = args[0];
        String name = args[1];
        String owner = args[2];

        WorldGenerator generator;
        try {
            generator = WorldGenerator.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage(StringUtils.format("&cUnknown world type (" + type + ")!"));
            return;
        }

        if (Bukkit.getWorld(name) != null) {
            sender.sendMessage(StringUtils.format("&cWorld already exists (" + name + ")!"));
            return;
        }

        Player player = Player.getOnlinePlayer(owner);
        if (player == null) {
            sender.sendMessage(MessageUtils.getUnknownPlayerMessage(owner));
            return;
        }

        generator.generate(name);
        if (generator == WorldGenerator.EMPTY)
            FileUtils.registerBukkitYmlWorld(name, "empty");

        WorldData data = new WorldData(name, player.getUniqueId(), WorldData.RestrictionType.DEFAULT, new HashSet<>(), generator);
        WorldUtils.saveWorldData(data);

        sender.sendMessage(StringUtils.format("&aCreated world (" + name + ") for " + player.getName() + "."));
    }

    @Override
    public String getDescription() {
        return "Generates a world for a player";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label + " <Type> <Name> <Owner>";
    }

    @Override
    public String getLabel() {
        return "generateworld";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"genworld", "newworld", "createworld", "gw"};
    }

    @Override
    public boolean allowExecution(Player player) {
        return player.roleAtLeast(Player.Role.OWNER);
    }
}
