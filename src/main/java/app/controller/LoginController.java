package app.controller;

import app.model.User;
import app.persistence.JsonLoader;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.List;

public class LoginController {

    private MainController mainController;
    private List<User> users;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        this.users = JsonLoader.loadUsers();
    }

    @FXML
    private void handleLogin() {
        if (mainController == null || users == null
                || usernameField == null || passwordField == null) {
            return;
        }

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            return;
        }

        User user = users.stream()
                .filter(u -> username.equals(u.getName())
                        && password.equals(u.getPassword()))
                .findFirst()
                .orElse(null);

        if (user == null) {
            return;
        }

        String role = user.getRole();
        if ("Customer".equalsIgnoreCase(role)) {
            mainController.showCustomerScreen();
        } else if ("Barista".equalsIgnoreCase(role)) {
            mainController.showBaristaScreen();
        } else if ("Manager".equalsIgnoreCase(role)) {
            mainController.showManagerMenuScreen();
        } else {
            mainController.showCustomerScreen();
        }
    }

    public void handleCustomerContinue() {
        mainController.showCustomerScreen();
    }
}
