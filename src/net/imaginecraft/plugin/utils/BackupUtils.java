package net.imaginecraft.plugin.utils;

import net.imaginecraft.plugin.ImagineCraft;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class BackupUtils {
    public static final List<String> ignoredFiles;

    static {
        ignoredFiles = new ArrayList<>();
        ignoredFiles.add("logs");
        ignoredFiles.add("backup");
    }

    private BackupUtils() {}


    public static void saveWorlds() {
        for (World worlds : Bukkit.getWorlds())
            worlds.save();
    }


    public static void scheduleBackups() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ImagineCraft.getInstance(), BackupUtils::backup, 20 * 60 * 5, 20 * 60 * 5); // every 5 mins
    }

    public static void deleteFolderContents(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory())
                    deleteFolderContents(f);
                if (!f.delete())
                    LogUtils.error("Could not delete file inside directory " + folder.getName() + "!");
            }
        }
    }
    public static void copyFiles(Path sourcePath, Path targetPath) throws IOException {
        Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if (ignoredFiles.contains(dir.getFileName().toString()))
                    return FileVisitResult.SKIP_SUBTREE;
                Files.createDirectories(targetPath.resolve(sourcePath.relativize(dir)));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (ignoredFiles.contains(file.getFileName().toString()))
                    return FileVisitResult.SKIP_SUBTREE;
                Files.copy(file, targetPath.resolve(sourcePath.relativize(file)));
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void backup() {
        LogUtils.info("Backing up server...");
        // save worlds
        saveWorlds();

        File target = FileUtils.getBackupFolder();
        // clear backup folder
        deleteFolderContents(target);

        File source = FileUtils.getServerFolder();
        // copy files
        try {
            copyFiles(source.toPath(), target.toPath());
        } catch (IOException e) {
            LogUtils.error("IOException while attempting to backup!");
            e.printStackTrace();
        }
        LogUtils.info("Done with backup.");
    }
}
