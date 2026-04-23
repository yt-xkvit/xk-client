package com.xkclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Main entry point for the XK Client Minecraft Launcher
 */
public class XKClient extends Application {
    private static final Logger logger = LoggerFactory.getLogger(XKClient.class);
    private static final String APP_NAME = "XK Client";
    private static final String APP_VERSION = "1.0.0";

    @Override
    public void start(Stage primaryStage) {
        try {
            logger.info("Starting {} v{}", APP_NAME, APP_VERSION);

            // Load FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
            javafx.scene.layout.BorderPane root = loader.load();

            // Create scene
            Scene scene = new Scene(root, 1200, 700);

            // Set window properties
            primaryStage.setTitle(APP_NAME + " v" + APP_VERSION);
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);

            primaryStage.show();
            logger.info("{} started successfully", APP_NAME);
        } catch (IOException e) {
            logger.error("Failed to load FXML", e);
            System.exit(1);
        } catch (Exception e) {
            logger.error("Failed to start application", e);
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        logger.info("Launching XK Client v{}", APP_VERSION);
        launch(args);
    }
}
