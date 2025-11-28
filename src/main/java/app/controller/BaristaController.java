package app.controller;

import app.model.InventoryManager;
import app.model.MenuItem;
import app.model.Order;
import app.model.OrderItem;
import app.model.OrderManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

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
        refreshOrders();
    }

    public void setInventoryManager(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @FXML
    private void initialize() {
        if (ordersTable != null) {
            ordersTable.setItems(orders);
        }
        refreshOrders();
    }

    private void refreshOrders() {
        if (orderManager != null) {
            orders.setAll(orderManager.getActiveOrders());
        }
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

        StringBuilder sb = new StringBuilder();
        sb.append("Order ").append(selected.getId()).append("\n");
        sb.append("Status: ").append(selected.getStatus()).append("\n\n");
        sb.append("Items:\n");
        for (OrderItem item : selected.getItems()) {
            MenuItem mi = item.getMenuItem();
            sb.append("- ")
              .append(mi.getName())
              .append(" x").append(item.getQuantity())
              .append(" -> $").append(item.getSubtotal())
              .append("\n");
        }
        sb.append("\nTotal: $").append(selected.getTotal());

        orderDetailsArea.setText(sb.toString());
    }

    @FXML
    private void handleMarkPrepared() {
        if (ordersTable == null || orderDetailsArea == null
                || orderManager == null || inventoryManager == null) {
            return;
        }

        Order selected = ordersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }

        boolean ok = inventoryManager.consumeIngredients(selected);
        if (!ok) {
            orderDetailsArea.appendText("\n\nNot enough inventory to prepare this order.");
            return;
        }

        orderManager.markPrepared(selected);
        orderManager.markCompleted(selected);

        orderDetailsArea.appendText("\n\nOrder marked prepared and completed.");
        refreshOrders();
    }

    @FXML
    private void handleLogout() {
        if (mainController != null) {
            mainController.showLoginScreen();
        }
    }
}
