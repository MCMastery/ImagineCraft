package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.commands.overridden.OverriddenKillCommand;
import net.imaginecraft.plugin.commands.overridden.OverriddenMeCommand;
import net.imaginecraft.plugin.commands.overridden.OverriddenTellCommand;
import net.imaginecraft.plugin.commands.overridden.OverriddenVersionCommand;
import net.imaginecraft.plugin.utils.LogUtils;
import net.imaginecraft.plugin.utils.MessageUtils;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// represents a command which the console and players can send
public abstract class Command {
    private static List<Command> commands = new ArrayList<>();

    public static List<Command> getCommands() {
        return commands;
    }
    public static Command fromLabel(String label) {
        for (Command command : commands) {
            if (command.getLabel().equalsIgnoreCase(label))
                return command;
            for (String alias : command.getAliases())
                if (alias.equalsIgnoreCase(label))
                    return command;
        }
        return null;
    }
    public static void registerCommands() {
        commands.add(new WorldTeleportCommand());
        commands.add(new GenerateWorldCommand());
        commands.add(new ClearCommand());
        commands.add(new BalanceCommand());
        commands.add(new ResetConfigCommand());
        commands.add(new ReloadConfigCommand());
        commands.add(new HelpCommand());
        commands.add(new MessageCommand());
        commands.add(new WarningsCommand());
        commands.add(new ReplyCommand());
        commands.add(new ClaimCommand());
        commands.add(new OwnerCommand());
        commands.add(new ClaimToolCommand());
        commands.add(new SetSpawnCommand());
        commands.add(new RestartCommand());
        commands.add(new StopCommand());
        commands.add(new SpawnCommand());
        commands.add(new SetRoleCommand());
        commands.add(new TeleportCommand());
        commands.add(new WarnCommand());
        commands.add(new TeleportAcceptCommand());
        commands.add(new TeleportDenyCommand());
        commands.add(new KickCommand());
        commands.add(new WorldsCommand());
        commands.add(new WorldPanelCommand());
        commands.add(new LocationCommand());
        commands.add(new UnclaimCommand());
        commands.add(new OverriddenVersionCommand());
        commands.add(new OverriddenKillCommand());
        commands.add(new OverriddenMeCommand());
        commands.add(new OverriddenTellCommand());

        // sort by label alphabetically
        Collections.sort(commands, (cmd1, cmd2) -> cmd1.getLabel().compareTo(cmd2.getLabel()));
    }

    // run a command, given a string like "/command arg1 arg2"
    // (WEIRD) returns the label as a STRING if it is an unknown command (WEIRD), otherwise NULL (WEIRD)
    public static String parseAndExecute(CommandSender sender, String message) {
        String[] split = message.split(" ");
        if (split.length == 0) {
            // this should never happen (it would be Bukkit's fault)
            LogUtils.error("CommandEvent triggered with empty command!");
            return null;
        }

        // parse label
        String label = split[0].substring(1); // remove the "/"
        // parse arguments
        String[] args = new String[split.length - 1];
        System.arraycopy(split, 1, args, 0, split.length - 1);

        Command command = Command.fromLabel(label);
        if (command == null)
            return label;

        if (sender instanceof org.bukkit.entity.Player) {
            Player player = Player.getPlayer((org.bukkit.entity.Player) sender);
            if (!command.allowExecution(player)) {
                player.sendMessage(MessageUtils.getForbiddenCommandMessage());
                return null;
            }
        }
        command.onExecute(sender, label, args);
        return null;
    }

    public String getUsageMessage(String label) {
        return MessageUtils.getCommandUsageMessage(getUsage(label));
    }

    // used to tab-complete, returns null if it does not much
    public String getMatch(String s) {
        // favor the label over aliases
        if (getLabel().startsWith(s))
            return getLabel();
        for (String alias : getAliases())
            if (alias.startsWith(s))
                return alias;
        return null;
    }


    public abstract String getDescription();
    public abstract String getUsage(String label);
    public abstract String getLabel();
    public abstract String[] getAliases();
    public abstract void onExecute(CommandSender sender, String label, String[] args);
    public abstract boolean allowExecution(Player player);
}
