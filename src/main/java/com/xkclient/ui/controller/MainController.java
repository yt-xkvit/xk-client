package com.xkclient.ui.controller;

import com.xkclient.service.AccountService;
import com.xkclient.service.ConfigService;
import com.xkclient.service.InstanceService;
import com.xkclient.service.ServerService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    private ConfigService configService;
    private InstanceService instanceService;
    private AccountService accountService;
    private ServerService serverService;

    @FXML
    public void initialize() {
        logger.info("Initializing MainController");
        initializeServices();
        updateStatus("Ready");
    }

    private void initializeServices() {
        configService = new ConfigService();
        instanceService = new InstanceService(configService);
        accountService = new AccountService(configService);
        serverService = new ServerService(configService);
    }

    public void updateStatus(String status) {
        if (statusLabel != null) {
            statusLabel.setText(status);
        }
    }

    // Getters for services
    public InstanceService getInstanceService() { return instanceService; }
    public AccountService getAccountService() { return accountService; }
    public ServerService getServerService() { return serverService; }
    public ConfigService getConfigService() { return configService; }
}
