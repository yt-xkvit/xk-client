package com.xkclient.ui.controller;

import com.xkclient.model.Account;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Controller for accounts tab
 */
public class AccountsController {
    private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);

    @FXML private ListView<Account> accountList;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private TextField uuidField;
    @FXML private CheckBox offlineCheckBox;
    @FXML private Label activeAccountLabel;

    private MainController mainController;
    private Account selectedAccount;

    @FXML
    public void initialize() {
        logger.info("Initializing AccountsController");
    }

    @FXML
    public void addAccount() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String uuid = uuidField.getText();

        if (username.isEmpty() || email.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter username and email");
            return;
        }

        try {
            Account account = new Account(username, email, uuid);
            account.setOffline(offlineCheckBox.isSelected());
            mainController.getAccountService().addAccount(account);
            logger.info("Added account: {}", username);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Account added successfully");
            clearFields();
            refreshAccountList();
        } catch (Exception e) {
            logger.error("Failed to add account", e);
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add account: " + e.getMessage());
        }
    }

    @FXML
    public void removeAccount() {
        if (selectedAccount == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select an account");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Account");
        confirm.setContentText("Are you sure you want to remove " + selectedAccount.getUsername() + "?");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            mainController.getAccountService().removeAccount(selectedAccount.getUsername());
            logger.info("Removed account: {}", selectedAccount.getUsername());
            refreshAccountList();
        }
    }

    @FXML
    public void onAccountSelected() {
        selectedAccount = accountList.getSelectionModel().getSelectedItem();
        if (selectedAccount != null) {
            activeAccountLabel.setText("Selected: " + selectedAccount.getUsername());
            usernameField.setText(selectedAccount.getUsername());
            emailField.setText(selectedAccount.getEmail());
            uuidField.setText(selectedAccount.getUuid());
            offlineCheckBox.setSelected(selectedAccount.isOffline());
        }
    }

    public void refreshAccountList() {
        List<Account> accounts = mainController.getAccountService().getAccounts();
        accountList.getItems().setAll(accounts);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        refreshAccountList();
    }

    private void clearFields() {
        usernameField.clear();
        emailField.clear();
        uuidField.clear();
        offlineCheckBox.setSelected(false);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
