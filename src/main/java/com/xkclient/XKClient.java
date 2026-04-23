package com.xkclient;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

            // Set window properties
            primaryStage.setTitle(APP_NAME + " v" + APP_VERSION);
            primaryStage.setWidth(1200);
            primaryStage.setHeight(700);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);

            // Create basic scene for now
            BorderPane root = new BorderPane();
            Label label = new Label("XK Client - Minecraft Launcher\nVersion " + APP_VERSION + "\n\nLoading...");
            label.setStyle("-fx-font-size: 18px; -fx-text-alignment: center; -fx-padding: 20;");
            root.setCenter(label);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

            logger.info("{} started successfully", APP_NAME);
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
