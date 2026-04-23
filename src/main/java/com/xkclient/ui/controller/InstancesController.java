package com.xkclient.ui.controller;

import com.xkclient.model.GameInstance;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Controller for instances tab
 */
public class InstancesController {
    private static final Logger logger = LoggerFactory.getLogger(InstancesController.class);

    @FXML private ListView<GameInstance> instanceList;
    @FXML private TextField instanceNameField;
    @FXML private ComboBox<String> versionCombo;
    @FXML private Spinner<Integer> maxMemorySpinner;
    @FXML private Spinner<Integer> minMemorySpinner;
    @FXML private TextArea jvmArgsArea;
    @FXML private Label selectedInstanceLabel;

    private MainController mainController;
    private GameInstance selectedInstance;

    @FXML
    public void initialize() {
        logger.info("Initializing InstancesController");
        setupSpinners();
        setupVersions();
    }

    private void setupSpinners() {
        SpinnerValueFactory<Integer> maxFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 16, 4);
        SpinnerValueFactory<Integer> minFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 8, 2);
        maxMemorySpinner.setValueFactory(maxFactory);
        minMemorySpinner.setValueFactory(minFactory);
    }

    private void setupVersions() {
        versionCombo.getItems().addAll(
                "1.8.9", "1.12.2", "1.16.5", "1.19.3", "1.20.1"
        );
        versionCombo.getSelectionModel().selectFirst();
    }

    @FXML
    public void createInstance() {
        String name = instanceNameField.getText();
        String version = versionCombo.getValue();

        if (name.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter an instance name");
            return;
        }

        try {
            GameInstance instance = mainController.getInstanceService().createInstance(name, version);
            logger.info("Created instance: {}", name);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Instance created successfully");
            instanceNameField.clear();
            refreshInstanceList();
        } catch (Exception e) {
            logger.error("Failed to create instance", e);
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to create instance: " + e.getMessage());
        }
    }

    @FXML
    public void deleteInstance() {
        if (selectedInstance == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select an instance");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Instance");
        confirm.setContentText("Are you sure you want to delete " + selectedInstance.getName() + "?");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            mainController.getInstanceService().deleteInstance(selectedInstance.getName());
            logger.info("Deleted instance: {}", selectedInstance.getName());
            refreshInstanceList();
        }
    }

    @FXML
    public void editInstance() {
        if (selectedInstance == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select an instance");
            return;
        }

        selectedInstance.setMaxMemory(maxMemorySpinner.getValue());
        selectedInstance.setMinMemory(minMemorySpinner.getValue());
        selectedInstance.setJvmArguments(jvmArgsArea.getText());
        mainController.getInstanceService().updateInstance(selectedInstance);
        logger.info("Updated instance: {}", selectedInstance.getName());
        showAlert(Alert.AlertType.INFORMATION, "Success", "Instance updated successfully");
    }

    @FXML
    public void onInstanceSelected() {
        selectedInstance = instanceList.getSelectionModel().getSelectedItem();
        if (selectedInstance != null) {
            selectedInstanceLabel.setText("Selected: " + selectedInstance.getName());
            maxMemorySpinner.getValueFactory().setValue(selectedInstance.getMaxMemory());
            minMemorySpinner.getValueFactory().setValue(selectedInstance.getMinMemory());
            jvmArgsArea.setText(selectedInstance.getJvmArguments());
        }
    }

    public void refreshInstanceList() {
        List<GameInstance> instances = mainController.getInstanceService().getInstances();
        instanceList.getItems().setAll(instances);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        refreshInstanceList();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
