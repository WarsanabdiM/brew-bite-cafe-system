package app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;


public class LoginController {

    private MainController mainController;

    @FXML
    private TextField usernameField;    // optional, 

    @FXML
    private ChoiceBox<String> roleChoiceBox;

    @FXML
    private Button loginButton;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void initialize() {
        if (roleChoiceBox != null) {
            roleChoiceBox.getItems().addAll("Customer", "Barista", "Manager");
            roleChoiceBox.setValue("Customer");
        }
    }

    @FXML
    private void handleLogin() {
        if (mainController == null || roleChoiceBox == null) {
            return;
        }

        String role = roleChoiceBox.getValue();

        // TODO: later add real validation using User model.
        switch (role) {
            case "Customer" -> mainController.showCustomerScreen();
            case "Barista" -> mainController.showBaristaScreen();
            case "Manager" -> mainController.showManagerMenuScreen();
            default -> mainController.showCustomerScreen();
        }
    }
}
