package app.controller;

import app.factory.BeverageFactory;
import app.factory.PastryFactory;

import app.model.item.MenuItem;
import app.model.item.Beverage;
import app.model.item.Pastry;

import app.model.menu.MenuManager;
import app.model.ingredient.InventoryManager;
import app.model.order.OrderManager;

import app.model.observer.Observer;
import app.model.observer.Observable;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.Map;

public class ManagerController implements Observer {

    private final MainController main;
    private final MenuManager menuManager;
    private final InventoryManager inventoryManager;
    private final OrderManager orderManager;

    private final BeverageFactory beverageFactory = new BeverageFactory();
    private final PastryFactory pastryFactory = new PastryFactory();

    @FXML private StackPane content;

    public ManagerController(MainController main,
                             MenuManager menuManager,
                             InventoryManager inventoryManager,
                             OrderManager orderManager) {

        this.main = main;
        this.menuManager = menuManager;
        this.inventoryManager = inventoryManager;
        this.orderManager = orderManager;

        menuManager.addObserver(this);
        inventoryManager.addObserver(this);
        orderManager.addObserver(this);
    }

    @FXML
    private void initialize() {
        // Manager screen loads subviews later
    }

    @FXML
    private void handleLogout() {
        main.showLogin();
    }

    @Override
    public void update(Observable o) {
        // Manager dashboard refresh logic here
    }
}