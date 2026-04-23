package com.xkclient.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class for version management
 */
public class VersionManager {
    private static final Logger logger = LoggerFactory.getLogger(VersionManager.class);

    /**
     * Get installed Minecraft versions
     */
    public static String[] getInstalledVersions() {
        Path versionsPath = Paths.get(System.getProperty("user.home"), ".minecraft", "versions");
        try {
            return Files.list(versionsPath)
                    .filter(Files::isDirectory)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .toArray(String[]::new);
        } catch (IOException e) {
            logger.error("Failed to get installed versions", e);
            return new String[0];
        }
    }

    /**
     * Check if a version is installed
     */
    public static boolean isVersionInstalled(String version) {
        Path versionPath = Paths.get(System.getProperty("user.home"), ".minecraft", "versions", version);
        return Files.exists(versionPath);
    }

    /**
     * Get version metadata
     */
    public static String getVersionInfo(String version) {
        Path versionPath = Paths.get(System.getProperty("user.home"), ".minecraft", "versions", version);
        StringBuilder info = new StringBuilder();
        info.append("Version: ").append(version).append("\n");
        info.append("Installed: ").append(Files.exists(versionPath)).append("\n");

        if (Files.exists(versionPath)) {
            try {
                long size = Files.walk(versionPath)
                        .filter(Files::isRegularFile)
                        .mapToLong(p -> {
                            try {
                                return Files.size(p);
                            } catch (IOException e) {
                                return 0;
                            }
                        })
                        .sum();
                info.append("Size: ").append(formatBytes(size)).append("\n");
            } catch (IOException e) {
                logger.error("Failed to get version size", e);
            }
        }

        return info.toString();
    }

    /**
     * Format bytes to human readable format
     */
    private static String formatBytes(long bytes) {
        if (bytes <= 0) return "0 B";
        final String[] units = new String[]{"B", "KB", "MB", "GB"};
        int digitGroups = (int) (Math.log10(bytes) / Math.log10(1024));
        return String.format("%.1f %s", bytes / Math.pow(1024, digitGroups), units[digitGroups]);
    }
}
