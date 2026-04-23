package com.xkclient.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for launching Minecraft
 */
public class GameLauncher {
    private static final Logger logger = LoggerFactory.getLogger(GameLauncher.class);
    private static final String JAVA_EXECUTABLE = "java";

    /**
     * Launch Minecraft with given parameters
     */
    public static Process launchGame(String instancePath, String version, String javaPath,
                                      int maxMemory, int minMemory, String jvmArgs,
                                      String username, String uuid) throws IOException {
        List<String> command = buildCommand(instancePath, version, javaPath, maxMemory, minMemory, jvmArgs, username, uuid);
        logger.info("Launching game with command: {}", String.join(" ", command));

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.directory(new File(instancePath));
        pb.inheritIO();

        return pb.start();
    }

    /**
     * Build the launch command
     */
    private static List<String> buildCommand(String instancePath, String version, String javaPath,
                                             int maxMemory, int minMemory, String jvmArgs,
                                             String username, String uuid) {
        List<String> command = new ArrayList<>();
        command.add(javaPath != null ? javaPath : JAVA_EXECUTABLE);

        // Memory settings
        command.add("-Xmx" + maxMemory + "G");
        command.add("-Xms" + minMemory + "G");

        // Custom JVM arguments
        if (jvmArgs != null && !jvmArgs.isEmpty()) {
            for (String arg : jvmArgs.split(" ")) {
                if (!arg.isEmpty()) {
                    command.add(arg);
                }
            }
        }

        // Game class and arguments
        command.add("-cp");
        command.add(getClasspath(instancePath));
        command.add("net.minecraft.client.main.Main");
        command.add("--username");
        command.add(username);
        command.add("--uuid");
        command.add(uuid);
        command.add("--gameDir");
        command.add(instancePath);
        command.add("--assetsDir");
        command.add(getAssetsPath());

        return command;
    }

    /**
     * Get the classpath for the game
     */
    private static String getClasspath(String instancePath) {
        Path libsPath = Paths.get(instancePath, "libs");
        StringBuilder classpath = new StringBuilder();

        try {
            Files.list(libsPath)
                    .filter(p -> p.toString().endsWith(".jar"))
                    .forEach(p -> {
                        if (classpath.length() > 0) {
                            classpath.append(File.pathSeparator);
                        }
                        classpath.append(p.toString());
                    });
        } catch (IOException e) {
            logger.error("Failed to build classpath", e);
        }

        return classpath.toString();
    }

    /**
     * Get Minecraft assets directory
     */
    private static String getAssetsPath() {
        String userHome = System.getProperty("user.home");
        return Paths.get(userHome, ".minecraft", "assets").toString();
    }

    /**
     * Check if Java is installed
     */
    public static boolean isJavaInstalled() {
        try {
            Process process = new ProcessBuilder(JAVA_EXECUTABLE, "-version").start();
            return process.waitFor() == 0;
        } catch (Exception e) {
            logger.error("Java not found", e);
            return false;
        }
    }
}
