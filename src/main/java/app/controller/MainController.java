package app.controller;

import app.model.Inventory;
import app.model.InventoryManager;
import app.model.MenuManager;
import app.model.OrderManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane root;

    private final MenuManager menuManager = new MenuManager();
    private final OrderManager orderManager = new OrderManager();
    private final InventoryManager inventoryManager = new InventoryManager(new Inventory());

    @FXML
    public void initialize() {
        showLoginScreen();
    }

    private void loadScreen(String fxmlPath, Object controller) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            Object loadedController = loader.getController();

            if (loadedController instanceof LoginController lc) {
                lc.setMainController(this);
            } else if (loadedController instanceof CustomerController cc) {
                cc.setMainController(this);
                cc.setMenuManager(menuManager);
                cc.setOrderManager(orderManager);
            } else if (loadedController instanceof BaristaController bc) {
                bc.setMainController(this);
                bc.setOrderManager(orderManager);
                bc.setInventoryManager(inventoryManager);
            } else if (loadedController instanceof ManagerController mc) {
                mc.setMainController(this);
                mc.setMenuManager(menuManager);
                mc.setInventoryManager(inventoryManager);
            }

            root.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLoginScreen() {
        loadScreen("/app/view/LoginView.fxml", new LoginController());
    }

    public void showCustomerScreen() {
        loadScreen("/app/view/CustomerView.fxml", new CustomerController());
    }

    public void showBaristaScreen() {
        loadScreen("/app/view/BaristaView.fxml", new BaristaController());
    }

    public void showManagerMenuScreen() {
        loadScreen("/app/view/ManagerMenuView.fxml", new ManagerController());
    }
}
