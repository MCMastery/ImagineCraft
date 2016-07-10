package net.imaginecraft.plugin.utils;

import net.imaginecraft.plugin.ImagineCraft;
import org.bukkit.Bukkit;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

// https://bukkit.org/threads/tutorial-use-external-library-s-with-your-plugin.103781/
public class JarUtils {
    private JarUtils() {}

    public static void loadLibraries() {
        // load libraries
        try {
            File[] libs = new File[] {new File(FileUtils.getServerFolder(), "commons-net-3.5.jar")};
            for (File lib : libs)
                if (!lib.exists())
                    JarUtils.extractFromJar(lib.getName(), lib.getAbsolutePath());
            for (File lib : libs) {
                if (!lib.exists()) {
                    LogUtils.error("SEVERE! Could not find library " + lib.getName() + "!!! Place it in the server root folder!");
                    Bukkit.getServer().getPluginManager().disablePlugin(ImagineCraft.getInstance());
                    return;
                }
                addClassPath(JarUtils.getJarUrl(lib));
            }
        } catch (Exception e) {
            LogUtils.error("SEVERE! Could not load libraries!!! Place them in the server root folder!");
            e.printStackTrace();
        }
    }

    private static void addClassPath(URL url) throws IOException {
        URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class<URLClassLoader> sysclass = URLClassLoader.class;
        try {
            Method method = sysclass.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(sysloader, url);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new IOException("Error adding " + url + " to system classloader");
        }
    }

    public static boolean extractFromJar(final String fileName,
                                         final String dest) throws IOException {
        if (getRunningJar() == null) {
            return false;
        }
        final File file = new File(dest);
        if (file.isDirectory()) {
            file.mkdir();
            return false;
        }
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }

        final JarFile jar = getRunningJar();
        final Enumeration<JarEntry> e = jar.entries();
        while (e.hasMoreElements()) {
            final JarEntry je = e.nextElement();
            if (!je.getName().contains(fileName)) {
                continue;
            }
            final InputStream in = new BufferedInputStream(
                    jar.getInputStream(je));
            final OutputStream out = new BufferedOutputStream(
                    new FileOutputStream(file));
            copyInputStream(in, out);
            jar.close();
            return true;
        }
        jar.close();
        return false;
    }

    private final static void copyInputStream(final InputStream in,
                                              final OutputStream out) throws IOException {
        try {
            final byte[] buff = new byte[4096];
            int n;
            while ((n = in.read(buff)) > 0) {
                out.write(buff, 0, n);
            }
        } finally {
            out.flush();
            out.close();
            in.close();
        }
    }

    public static URL getJarUrl(final File file) throws IOException {
        return new URL("jar:" + file.toURI().toURL().toExternalForm() + "!/");
    }

    public static JarFile getRunningJar() throws IOException {
        if (!RUNNING_FROM_JAR) {
            return null; // null if not running from jar
        }
        String path = new File(JarUtils.class.getProtectionDomain()
                .getCodeSource().getLocation().getPath()).getAbsolutePath();
        path = URLDecoder.decode(path, "UTF-8");
        return new JarFile(path);
    }

    private static boolean RUNNING_FROM_JAR = false;

    static {
        final URL resource = JarUtils.class.getClassLoader()
                .getResource("plugin.yml");
        if (resource != null) {
            RUNNING_FROM_JAR = true;
        }
    }

}
