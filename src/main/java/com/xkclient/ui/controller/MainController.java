package com.xkclient.ui.controller;

import com.xkclient.service.AccountService;
import com.xkclient.service.ConfigService;
import com.xkclient.service.InstanceService;
import com.xkclient.service.ServerService;
import com.xkclient.util.PerformanceMonitor;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main controller for the application
 */
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @FXML private BorderPane mainPane;
    @FXML private Label statusLabel;
    @FXML private Label bottomStatusLabel;
    @FXML private Label javaVersionLabel;
    @FXML private TextArea systemInfoArea;
    @FXML private ProgressBar memoryProgressBar;
    @FXML private ProgressBar cpuProgressBar;
    @FXML private Label memoryLabel;
    @FXML private Label cpuLabel;

    private ConfigService configService;
    private InstanceService instanceService;
    private AccountService accountService;
    private ServerService serverService;
    private AnimationTimer performanceTimer;

    @FXML
    public void initialize() {
        logger.info("Initializing MainController");
        initializeServices();
        setupPerformanceMonitoring();
        updateSystemInfo();
        updateStatus("Ready");
    }

    private void initializeServices() {
        configService = new ConfigService();
        instanceService = new InstanceService(configService);
        accountService = new AccountService(configService);
        serverService = new ServerService(configService);
    }

    private void setupPerformanceMonitoring() {
        javaVersionLabel.setText(System.getProperty("java.version"));
        
        performanceTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updatePerformanceMetrics();
            }
        };
        performanceTimer.start();
    }

    private void updatePerformanceMetrics() {
        long memUsed = PerformanceMonitor.getMemoryUsageMB();
        long memMax = PerformanceMonitor.getMaxMemoryMB();
        double memPercent = (double) memUsed / memMax;
        
        memoryProgressBar.setProgress(memPercent);
        memoryLabel.setText(memUsed + " MB / " + memMax + " MB");
        
        double cpuUsage = PerformanceMonitor.getCpuUsage();
        cpuProgressBar.setProgress(Math.min(cpuUsage / 100, 1.0));
        cpuLabel.setText(String.format("%.2f%%", cpuUsage));
    }

    private void updateSystemInfo() {
        systemInfoArea.setText(PerformanceMonitor.getSystemInfo());
    }

    public void updateStatus(String status) {
        if (statusLabel != null) {
            statusLabel.setText(status);
        }
        if (bottomStatusLabel != null) {
            bottomStatusLabel.setText(status);
        }
    }

    @FXML
    public void openSettings() {
        logger.info("Opening settings");
        updateStatus("Opening settings...");
    }

    @FXML
    public void exitApplication() {
        logger.info("Exiting application");
        if (performanceTimer != null) {
            performanceTimer.stop();
        }
        System.exit(0);
    }

    // Getters for services
    public InstanceService getInstanceService() { return instanceService; }
    public AccountService getAccountService() { return accountService; }
    public ServerService getServerService() { return serverService; }
    public ConfigService getConfigService() { return configService; }
}
