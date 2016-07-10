package net.imaginecraft.plugin;

import net.imaginecraft.plugin.listeners.internal.playerjoin.PlayerJoinEvent;
import net.imaginecraft.plugin.listeners.internal.playerjoin.PlayerJoinListener;
import net.imaginecraft.plugin.listeners.internal.playerquit.PlayerQuitEvent;
import net.imaginecraft.plugin.listeners.internal.playerquit.PlayerQuitListener;
import net.imaginecraft.plugin.utils.LogUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerInfoUpdater implements PlayerJoinListener, PlayerQuitListener {
    public static void shutDown() throws IOException {
        LogUtils.info("Updating server info for server shut down...");
        update(Collections.singletonList("<span style=\"color: red\">Server offline</span>"));
        LogUtils.info("Done with update.");
    }

    public static List<String> getInfo(int currentPlayers) {
        List<String> info = new ArrayList<>();
        info.add("Players: " + currentPlayers + "/" + Bukkit.getMaxPlayers());
        return info;
    }

    private static void uploadToFTP(File file, String path) throws IOException {
        FTPClient client = new FTPClient();
        client.connect("107.180.50.178");
        client.setFileType(FTP.BINARY_FILE_TYPE);
        client.enterLocalPassiveMode();
        client.setAutodetectUTF8(true);
        client.login("dgrissom1", "Dpolite1");

        FileInputStream fis = new FileInputStream(file);
        client.storeFile("public_html/imaginecraft/" + path, fis);
        client.logout();
    }

    // the list of strings displays on the website, so we can use HTML in it
    private static void update(List<String> lines) throws IOException {
        File file = new File("server-info.txt");
        Files.write(file.toPath(), lines);
        uploadToFTP(file, "server-info.txt");
    }

    public static void update(int currentPlayers) {
        try {
            LogUtils.info("Updating server info...");
            update(getInfo(currentPlayers));
            LogUtils.info("Done with update.");
        } catch (IOException e) {
            LogUtils.error("IOException while attempting to update the server info!");
            e.printStackTrace();
        }
    }




    @Override
    public void onPlayerJoin(PlayerJoinEvent evt) {
        update(Bukkit.getOnlinePlayers().size());
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent evt) {
        update(Bukkit.getOnlinePlayers().size() - 1); // the player is removed from the list after playerquitevent
    }
}
