package app.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import app.model.menu.MenuManager;
import app.model.order.OrderManager;
import app.model.ingredient.InventoryManager;
import app.model.user.User;

public class MainController {

    private final Stage stage;

    private final MenuManager menuManager;
    private final OrderManager orderManager;
    private final InventoryManager inventoryManager;

    public MainController(Stage stage,
                          MenuManager menuManager,
                          OrderManager orderManager,
                          InventoryManager inventoryManager) {

        this.stage = stage;
        this.menuManager = menuManager;
        this.orderManager = orderManager;
        this.inventoryManager = inventoryManager;
    }

    private void loadScreen(String fxml, Object controller) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            loader.setController(controller);
            Parent root = loader.load();

            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

            stage.setTitle("Brew & Bite Cafe");
            stage.setScene(scene);
            stage.show();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
