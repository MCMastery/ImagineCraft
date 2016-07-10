package net.imaginecraft.plugin.listeners;

import net.imaginecraft.plugin.listeners.internal.playerinteract.PlayerInteractEvent;
import net.imaginecraft.plugin.listeners.internal.playerinteract.PlayerInteractListener;
import net.imaginecraft.plugin.utils.ConfigUtils;
import org.bukkit.Location;
import org.bukkit.event.block.Action;

import java.util.List;

public class LandClaimer implements PlayerInteractListener {
    @Override
    public void onPlayerInteract(PlayerInteractEvent evt) {
        if ((evt.getAction() == Action.RIGHT_CLICK_BLOCK  || evt.getAction() == Action.LEFT_CLICK_BLOCK) && evt.getItem() != null
                && evt.getItem().getType() == ConfigUtils.getLandClaimTool() && evt.getItem().getItemMeta().hasLore()) {
            // check lore
            List<String> lore = evt.getItem().getItemMeta().getLore();
            if (lore.size() == 0)
                return;
            if (lore.get(0).equals(ConfigUtils.getLandClaimToolLore())) {
                Location location = evt.getBlock().getLocation();
                boolean isPointOne = evt.getAction() == Action.LEFT_CLICK_BLOCK;
                if (isPointOne)
                    evt.getPlayer().setSelectionPointOne(location);
                else
                    evt.getPlayer().setSelectionPointTwo(location);
                evt.getPlayer().sendFormattedMessage("&aSelected Point #" + (isPointOne ? "1" : "2") + " (" + location.getBlockX() + ", " + location.getBlockZ() + ")");
                evt.setCancelled(true);
            }
        }
    }
}
