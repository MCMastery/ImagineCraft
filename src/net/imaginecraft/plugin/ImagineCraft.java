package net.imaginecraft.plugin;

import net.imaginecraft.plugin.commands.Command;
import net.imaginecraft.plugin.listeners.internal.*;
import net.imaginecraft.plugin.listeners.internal.blockbreak.InternalBlockBreakListener;
import net.imaginecraft.plugin.listeners.internal.blockplace.InternalBlockPlaceListener;
import net.imaginecraft.plugin.listeners.internal.bucketempty.InternalBucketEmptyListener;
import net.imaginecraft.plugin.listeners.internal.bucketfill.InternalBucketFillListener;
import net.imaginecraft.plugin.listeners.internal.entitybreakhanging.InternalEntityBreakHangingListener;
import net.imaginecraft.plugin.listeners.internal.entitydamageentity.InternalEntityDamageEntityListener;
import net.imaginecraft.plugin.listeners.internal.explosion.InternalExplosionListener;
import net.imaginecraft.plugin.listeners.internal.guiclick.InternalGUIClickListener;
import net.imaginecraft.plugin.listeners.internal.inventoryclose.InternalInventoryCloseListener;
import net.imaginecraft.plugin.listeners.internal.liquidflow.InternalBlockMoveListener;
import net.imaginecraft.plugin.listeners.internal.playerchat.InternalPlayerChatListener;
import net.imaginecraft.plugin.listeners.internal.playerinteract.InternalPlayerInteractListener;
import net.imaginecraft.plugin.listeners.internal.playerinteractentity.InternalPlayerInteractWithEntityListener;
import net.imaginecraft.plugin.listeners.internal.playerjoin.InternalPlayerJoinListener;
import net.imaginecraft.plugin.listeners.internal.playerquit.InternalPlayerQuitListener;
import net.imaginecraft.plugin.listeners.internal.serverlistping.InternalServerListPingListener;
import net.imaginecraft.plugin.listeners.internal.tabcomplete.InternalTabCompleteListener;
import net.imaginecraft.plugin.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class ImagineCraft extends JavaPlugin {
    private static ImagineCraft instance = null;

    @Override
    public void onEnable() {
        if (instance != null)
            LogUtils.error("onEnable() called twice!");
        instance = this;
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);

        JarUtils.loadLibraries();

        WorldUtils.loadWorlds();

        registerListeners();
        Command.registerCommands();
        ClaimManager.loadClaims();

        BackupUtils.scheduleBackups();

        ServerInfoUpdater.update(Bukkit.getOnlinePlayers().size());
    }

    @Override
    public void onDisable() {
        try {
            ServerInfoUpdater.shutDown();
        } catch (IOException e) {
            LogUtils.error("IOException while attempting to update the server info for the shut down!");
            e.printStackTrace();
        }
    }

    public static ImagineCraft getInstance() {
        return instance;
    }

    public static void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, instance);
    }
    private static void registerListeners() {
        InternalServerListPingListener.register();
        InternalExplosionListener.register();
        InternalBlockMoveListener.register();
        InternalBucketEmptyListener.register();
        InternalBucketFillListener.register();
        InternalBlockBreakListener.register();
        InternalPlayerQuitListener.register();
        InternalPlayerJoinListener.register();
        InternalPlayerCommandListener.register();
        InternalServerCommandListener.register();
        InternalBlockPlaceListener.register();
        InternalPlayerInteractListener.register();
        InternalPlayerChatListener.register();
        InternalEntityDamageEntityListener.register();
        InternalEntityBreakHangingListener.register();
        InternalPlayerInteractWithEntityListener.register();
        InternalTabCompleteListener.register();
        InternalGUIClickListener.register();
        InternalInventoryCloseListener.register();
    }


    //todo this not pretty.
    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        switch (id.toLowerCase()) {
            case "empty":
                return new EmptyWorldGenerator();
            default:
                LogUtils.error("Invalid chunk generator ID (" + id + ")!");
                return null;
        }
    }



    public static void restartServer() {
        // kick all players so we can have a custom message
        for (Player player : Player.getOnlinePlayers())
            player.kickFormatted(MessageUtils.getRestartMessage());
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                List<String> args = ManagementFactory.getRuntimeMXBean().getInputArguments();
                List<String> command = new ArrayList<>();
                command.add(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java.exe");
                command.addAll(args);
                command.add("-jar");
                command.add(new File(Bukkit.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getAbsolutePath());
                try {
                    new ProcessBuilder(command).start();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Bukkit.shutdown();
    }
}
