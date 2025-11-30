package app;

import app.controller.MainController;

import app.model.ingredient.Inventory;
import app.model.ingredient.InventoryManager;

import app.model.menu.MenuManager;
import app.model.order.OrderManager;

import app.persistence.JsonLoader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        var menuItems = JsonLoader.loadMenu();
        var users = JsonLoader.loadUsers();
        var inventory = JsonLoader.loadInventory();
        var existingOrders = JsonLoader.loadOrders();

        MenuManager menuManager = new MenuManager();
        menuItems.forEach(menuManager::addMenuItem);

        InventoryManager inventoryManager = new InventoryManager(inventory);
        
        OrderManager orderManager = new OrderManager();
        existingOrders.forEach(o -> {
            orderManager.createOrder(o.getCreatedBy());
        });

        
        MainController main = new MainController(
                stage,
                menuManager,
                orderManager,
                inventoryManager
        );

        
        main.showLogin();
    }

    public static void main(String[] args) {
        launch();
    }
}

