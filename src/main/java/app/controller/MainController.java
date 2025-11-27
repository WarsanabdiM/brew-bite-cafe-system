package app.controller;

import app.model.InventoryManager;
import app.model.MenuManager;
import app.model.OrderManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Central navigation controller for Brew Bite.
 * Holds the primary Stage and shared managers, and loads all screens.
 */
public class MainController {

    private final Stage primaryStage;
    private final InventoryManager inventoryManager;
    private final MenuManager menuManager;
    private final OrderManager orderManager;

    public MainController(Stage primaryStage,
                          InventoryManager inventoryManager,
                          MenuManager menuManager,
                          OrderManager orderManager) {
        this.primaryStage = primaryStage;
        this.inventoryManager = inventoryManager;
        this.menuManager = menuManager;
        this.orderManager = orderManager;
    }

    public void showLoginScreen() {
        loadScreen("/fxml/login.fxml", "Brew Bite - Login");
    }

    public void showCustomerScreen() {
        loadScreen("/fxml/customer_view.fxml", "Brew Bite - Customer");
    }

    public void showBaristaScreen() {
        loadScreen("/fxml/barista_view.fxml", "Brew Bite - Barista");
    }

    public void showManagerMenuScreen() {
        loadScreen("/fxml/manager_menu.fxml", "Brew Bite - Manager Menu");
    }

    public void showManagerInventoryScreen() {
        loadScreen("/fxml/manager_inventory.fxml", "Brew Bite - Manager Inventory");
    }

    
    private void loadScreen(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Object controller = loader.getController();

            // Inject dependencies into each controller type
            if (controller instanceof LoginController loginController) {
                loginController.setMainController(this);
            } else if (controller instanceof CustomerController customerController) {
                customerController.setMainController(this);
                customerController.setMenuManager(menuManager);
                customerController.setOrderManager(orderManager);
            } else if (controller instanceof BaristaController baristaController) {
                baristaController.setMainController(this);
                baristaController.setOrderManager(orderManager);
                baristaController.setInventoryManager(inventoryManager);
            } else if (controller instanceof ManagerController managerController) {
                managerController.setMainController(this);
                managerController.setMenuManager(menuManager);
                managerController.setInventoryManager(inventoryManager);
            }

            primaryStage.setTitle(title);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
