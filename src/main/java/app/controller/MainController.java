package app.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import app.model.*;
import app.model.InventoryManager;
import app.model.MenuManager;
import app.model.OrderManager;

import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane root;

    private final MenuManager menuManager = new MenuManager();
    private final OrderManager orderManager = new OrderManager();
    private final InventoryManager inventoryManager = new InventoryManager(new Inventory());

    private void loadScreen(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent view = loader.load();

            Object controller = loader.getController();

            if (controller instanceof LoginController c)
                c.setMainController(this);

            if (controller instanceof CustomerController c) {
                c.setMainController(this);
                c.setMenuManager(menuManager);
                c.setOrderManager(orderManager);
            }

            if (controller instanceof BaristaController c) {
                c.setMainController(this);
                c.setOrderManager(orderManager);
                c.setInventoryManager(inventoryManager);
            }

            if (controller instanceof ManagerController c) {
                c.setMainController(this);
                c.setMenuManager(menuManager);
                c.setInventoryManager(inventoryManager);
            }

            root.setCenter(view);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
    
            LoginController controller = loader.getController();
            controller.setMainController(this);
    
            stage.setScene(new Scene(root));
            stage.show();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public void showCustomerScreen() {
        loadScreen("/fxml/customer.fxml");
    }

    public void showBaristaScreen() {
        loadScreen("/fxml/barista.fxml");
    }

    public void showManagerMenuScreen() {
        loadScreen("/fxml/manager.fxml");
    }
}
