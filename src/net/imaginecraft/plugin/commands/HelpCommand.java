package net.imaginecraft.plugin.commands;

import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.utils.CommandUtils;
import net.imaginecraft.plugin.utils.MathUtils;
import net.imaginecraft.plugin.utils.MessageUtils;
import net.imaginecraft.plugin.utils.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HelpCommand extends Command {
    @Override
    public String getDescription() {
        return "Displays a list of commands";
    }

    @Override
    public String getUsage(String label) {
        return "/" + label + " [PageNumber]";
    }

    @Override
    public String getLabel() {
        return "help";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"?"};
    }

    @Override
    public void onExecute(CommandSender sender, String label, String[] args) {
        int page = 0;
        if (args.length > 0) {
            try {
                page = Integer.parseInt(args[0]) - 1;
            } catch (NumberFormatException e) {
                sender.sendMessage(getUsageMessage(label));
                return;
            }
        }

        // 9 lines since top line will be header (so 10 lines total)
        int maxLines = 9;
        List<String> book;
        if (sender instanceof org.bukkit.entity.Player) {
            Player player = Player.getPlayer((org.bukkit.entity.Player) sender);
            book = CommandUtils.getHelpBook(player);
        } else
            book = CommandUtils.getHelpBook();

        int pages = StringUtils.getPageCount(maxLines, book);
        page = MathUtils.clamp(page, 0, pages - 1); // - 1 so it is user-friendly
        String header = MessageUtils.getHelpHeader(page + 1, pages); // +1 since it starts at 0, and players will read this
        sender.sendMessage(header);

        List<String> pageContents = StringUtils.getPage(page, maxLines, book);
        for (String line : pageContents)
            sender.sendMessage(line);
    }

    @Override
    public boolean allowExecution(Player player) {
        return true;
    }
}
