package net.imaginecraft.plugin.listeners.internal.guiclick;

import net.imaginecraft.plugin.GUI;
import net.imaginecraft.plugin.ImagineCraft;
import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.listeners.GUIEventHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashSet;
import java.util.Set;

public class InternalGUIClickListener implements Listener {
    private static Set<GUIClickListener> listeners = new HashSet<>();

    // only call this for this class, not instances of this class
    public static void register() {
        ImagineCraft.registerListener(new InternalGUIClickListener());

        // register listeners
        listeners.add(new GUIEventHandler());
    }

    @EventHandler
    public void onGUIClick(InventoryClickEvent evt) {
        if (evt.isCancelled())
            return;

        Player player = Player.getPlayer((org.bukkit.entity.Player) evt.getWhoClicked());

        GUI gui = GUI.getGUI(player);
        if (gui == null)
            return;

        GUIClickEvent event = new GUIClickEvent(evt.getCurrentItem(), gui, player, evt.getAction(), evt.getRawSlot());

        // trigger events in listeners
        for (GUIClickListener listener : listeners) {
            listener.onGUIClick(event);

            // don't pass event on to other listeners once one listener cancels it
            if (event.isCancelled()) {
                evt.setCancelled(true);
                return;
            }
        }

        // now collect info of what the event's values are now, and edit Bukkit's event accordingly
        evt.setCurrentItem(event.getItem());
    }
}
