package net.imaginecraft.plugin.listeners;

import net.imaginecraft.plugin.GUI;
import net.imaginecraft.plugin.listeners.internal.guiclick.GUIClickEvent;
import net.imaginecraft.plugin.listeners.internal.guiclick.GUIClickListener;
import net.imaginecraft.plugin.listeners.internal.inventoryclose.InventoryCloseEvent;
import net.imaginecraft.plugin.listeners.internal.inventoryclose.InventoryCloseListener;
import net.imaginecraft.plugin.listeners.internal.playerquit.PlayerQuitEvent;
import net.imaginecraft.plugin.listeners.internal.playerquit.PlayerQuitListener;

public class GUIEventHandler implements GUIClickListener, InventoryCloseListener, PlayerQuitListener {
    @Override
    public void onGUIClick(GUIClickEvent evt) {
        evt.getGUI().onGUIClick(evt);
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent evt) {
        GUI gui = GUI.getGUI(evt.getPlayer());
        if (gui != null)
            gui.onClose();
    }
    @Override
    public void onPlayerQuit(PlayerQuitEvent evt) {
        GUI gui = GUI.getGUI(evt.getPlayer());
        if (gui != null)
            gui.onClose();
    }
}
