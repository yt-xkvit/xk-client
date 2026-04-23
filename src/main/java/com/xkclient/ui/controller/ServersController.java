package com.xkclient.ui.controller;

import com.xkclient.model.Server;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Controller for servers tab
 */
public class ServersController {
    private static final Logger logger = LoggerFactory.getLogger(ServersController.class);

    @FXML private ListView<Server> serverList;
    @FXML private TextField serverNameField;
    @FXML private TextField addressField;
    @FXML private Spinner<Integer> portSpinner;
    @FXML private TextArea descriptionArea;
    @FXML private CheckBox favoriteCheckBox;
    @FXML private Label selectedServerLabel;

    private MainController mainController;
    private Server selectedServer;

    @FXML
    public void initialize() {
        logger.info("Initializing ServersController");
        setupPortSpinner();
    }

    private void setupPortSpinner() {
        SpinnerValueFactory<Integer> portFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 65535, 25565);
        portSpinner.setValueFactory(portFactory);
    }

    @FXML
    public void addServer() {
        String name = serverNameField.getText();
        String address = addressField.getText();
        int port = portSpinner.getValue();

        if (name.isEmpty() || address.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter server name and address");
            return;
        }

        try {
            Server server = new Server(name, address, port);
            server.setDescription(descriptionArea.getText());
            server.setFavorite(favoriteCheckBox.isSelected());
            mainController.getServerService().addServer(server);
            logger.info("Added server: {}", name);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Server added successfully");
            clearFields();
            refreshServerList();
        } catch (Exception e) {
            logger.error("Failed to add server", e);
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add server: " + e.getMessage());
        }
    }

    @FXML
    public void removeServer() {
        if (selectedServer == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select a server");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Server");
        confirm.setContentText("Are you sure you want to remove " + selectedServer.getName() + "?");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            mainController.getServerService().removeServer(selectedServer.getName());
            logger.info("Removed server: {}", selectedServer.getName());
            refreshServerList();
        }
    }

    @FXML
    public void editServer() {
        if (selectedServer == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select a server");
            return;
        }

        selectedServer.setName(serverNameField.getText());
        selectedServer.setAddress(addressField.getText());
        selectedServer.setPort(portSpinner.getValue());
        selectedServer.setDescription(descriptionArea.getText());
        selectedServer.setFavorite(favoriteCheckBox.isSelected());
        mainController.getServerService().updateServer(selectedServer);
        logger.info("Updated server: {}", selectedServer.getName());
        showAlert(Alert.AlertType.INFORMATION, "Success", "Server updated successfully");
        refreshServerList();
    }

    @FXML
    public void onServerSelected() {
        selectedServer = serverList.getSelectionModel().getSelectedItem();
        if (selectedServer != null) {
            selectedServerLabel.setText("Selected: " + selectedServer.getName());
            serverNameField.setText(selectedServer.getName());
            addressField.setText(selectedServer.getAddress());
            portSpinner.getValueFactory().setValue(selectedServer.getPort());
            descriptionArea.setText(selectedServer.getDescription());
            favoriteCheckBox.setSelected(selectedServer.isFavorite());
        }
    }

    public void refreshServerList() {
        List<Server> servers = mainController.getServerService().getServers();
        serverList.getItems().setAll(servers);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        refreshServerList();
    }

    private void clearFields() {
        serverNameField.clear();
        addressField.clear();
        portSpinner.getValueFactory().setValue(25565);
        descriptionArea.clear();
        favoriteCheckBox.setSelected(false);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
