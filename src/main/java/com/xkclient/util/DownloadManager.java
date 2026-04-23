package com.xkclient.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class for downloading files
 */
public class DownloadManager {
    private static final Logger logger = LoggerFactory.getLogger(DownloadManager.class);
    private static final int BUFFER_SIZE = 8192;

    /**
     * Download a file from URL
     */
    public static void downloadFile(String urlString, String destinationPath) throws IOException {
        URL url = new URL(urlString);
        Path destination = Paths.get(destinationPath);

        // Create parent directories if they don't exist
        Files.createDirectories(destination.getParent());

        logger.info("Downloading from {} to {}", urlString, destinationPath);

        try (BufferedInputStream in = new BufferedInputStream(url.openStream());
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destination.toFile()))) {

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            logger.info("Download completed: {}", destinationPath);
        }
    }

    /**
     * Download file with progress callback
     */
    public static void downloadFileWithProgress(String urlString, String destinationPath,
                                                ProgressCallback callback) throws IOException {
        URL url = new URL(urlString);
        Path destination = Paths.get(destinationPath);

        Files.createDirectories(destination.getParent());

        long contentLength = url.openConnection().getContentLengthLong();
        long downloaded = 0;

        try (BufferedInputStream in = new BufferedInputStream(url.openStream());
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destination.toFile()))) {

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                downloaded += bytesRead;
                callback.onProgress(downloaded, contentLength);
            }
        }
    }

    /**
     * Progress callback interface
     */
    public interface ProgressCallback {
        void onProgress(long downloaded, long total);
    }
}
