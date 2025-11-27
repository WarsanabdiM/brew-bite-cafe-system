package app.controller;

import app.model.InventoryManager;
import app.model.Order;
import app.model.OrderManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

/**
 * Barista screen:
 * - shows pending orders
 * - allows marking them as prepared.
 */
public class BaristaController {

    private MainController mainController;
    private OrderManager orderManager;
    private InventoryManager inventoryManager;

    private final ObservableList<Order> orders =
            FXCollections.observableArrayList();

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private Button markPreparedButton;

    @FXML
    private TextArea orderDetailsArea;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setOrderManager(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public void setInventoryManager(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @FXML
    private void initialize() {
        if (ordersTable != null) {
            ordersTable.setItems(orders);
        }

        // TODO: When OrderManager API exists, load active orders:
        // if (orderManager != null) {
        //     orders.setAll(orderManager.getActiveOrders());
        // }
    }

    @FXML
    private void handleOrderSelection() {
        if (ordersTable == null || orderDetailsArea == null) {
            return;
        }

        Order selected = ordersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }

        // TODO: Use real fields from Order to show details.
        orderDetailsArea.setText("Order details:\n" + selected.toString());
    }

    @FXML
    private void handleMarkPrepared() {
        if (ordersTable == null || orderDetailsArea == null) {
            return;
        }

        Order selected = ordersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }

        // TODO: Update order status and inventory when model methods exist:
        // orderManager.markPrepared(selected);
        // inventoryManager.deductForOrder(selected);
        orderDetailsArea.appendText("\n\nMarked prepared. (Connect to model later.)");
    }

    @FXML
    private void handleLogout() {
        if (mainController != null) {
            mainController.showLoginScreen();
        }
    }
}
