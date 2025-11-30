package app.controller;

import app.model.user.User;
import app.persistence.JsonLoader;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.List;

public class LoginController {

    private final MainController main;
    private final List<User> users = JsonLoader.loadUsers();

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    public LoginController(MainController main) {
        this.main = main;
    }

    @FXML
    private void handleLogin() {
        String u = usernameField.getText();
        String p = passwordField.getText();

        User match = users.stream()
                .filter(x -> x.getName().equalsIgnoreCase(u)
                          && x.getPassword().equals(p))
                .findFirst()
                .orElse(null);

        if (match == null) return;

        switch (match.getRole().toLowerCase()) {
            case "customer" -> main.showCustomer(match);
            case "barista"  -> main.showBarista(match);
            case "manager"  -> main.showManager(match);
        }
    }

    @FXML
    private void handleCustomerContinue() {
        User guest = new User("guest", "Guest", "Customer", "");
        main.showCustomer(guest);
    }
}