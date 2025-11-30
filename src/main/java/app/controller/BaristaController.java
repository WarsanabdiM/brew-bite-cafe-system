package app.controller;

import app.model.order.Order;
import app.model.order.OrderManager;
import app.model.ingredient.InventoryManager;
import app.model.observer.Observer;
import app.model.observer.Observable;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class BaristaController implements Observer {

    private final MainController main;
    private final OrderManager orderManager;
    private final InventoryManager inventoryManager;

    @FXML private TableView<Order> ordersTable;
    @FXML private TextArea orderDetails;

    public BaristaController(MainController main,
                             OrderManager orderManager,
                             InventoryManager inventoryManager) {
        this.main = main;
        this.orderManager = orderManager;
        this.inventoryManager = inventoryManager;

        orderManager.addObserver(this);
    }

    @FXML
    private void initialize() {
        ordersTable.getItems().setAll(orderManager.getActiveOrders());
    }

    @FXML
    private void handlePrepare() {
        Order o = ordersTable.getSelectionModel().getSelectedItem();
        if (o == null) return;

        if (!inventoryManager.hasIngredientsFor(o)) {
            orderDetails.appendText("\nNot enough inventory!");
            return;
        }

        inventoryManager.consumeIngredients(o);
        orderManager.markInProgress(o);
        orderManager.markCompleted(o);
    }

    @FXML
    private void handleLogout() {
        main.showLogin();
    }

    @Override
    public void update(Observable o) {
        ordersTable.getItems().setAll(orderManager.getActiveOrders());
    }
}