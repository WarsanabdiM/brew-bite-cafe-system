package app.controller;

import app.model.Customization;
import app.model.MenuItem;
import app.model.MenuManager;
import app.model.Order;
import app.model.OrderItem;
import app.model.OrderManager;
import app.model.Size;
import app.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.util.Collections;
import java.util.List;

public class CustomerController {

    private MainController mainController;
    private MenuManager menuManager;
    private OrderManager orderManager;

    private final ObservableList<MenuItem> menuItems =
            FXCollections.observableArrayList();

    private Order currentOrder;

    @FXML
    private TableView<MenuItem> menuTable;

    @FXML
    private TextArea orderSummaryArea;

    @FXML
    private Button placeOrderButton;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
        if (menuManager != null && menuTable != null) {
            menuItems.setAll(menuManager.getAll());
        }
    }

    public void setOrderManager(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    @FXML
    private void initialize() {
        if (menuTable != null) {
            menuTable.setItems(menuItems);
        }
        if (menuManager != null) {
            menuItems.setAll(menuManager.getAll());
        }
    }

    @FXML
    private void handleMenuSelection() {
        if (menuTable == null || orderSummaryArea == null) {
            return;
        }

        MenuItem selected = menuTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }

        orderSummaryArea.setText("Selected item:\n" + selected.getName()
                + " ($" + selected.getBasePrice() + ")");
    }

    @FXML
    private void handlePlaceOrder() {
        if (menuTable == null || orderSummaryArea == null || orderManager == null) {
            return;
        }

        MenuItem selected = menuTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            orderSummaryArea.appendText("\n\nNo item selected.");
            return;
        }

        if (currentOrder == null) {
            User guest = new User("guest", "Guest", "Customer", "");
            currentOrder = orderManager.createOrder(guest);
        }

        Size size = Size.SMALL;
        List<Customization> customizations = Collections.emptyList();
        int quantity = 1;

        OrderItem item = new OrderItem(selected, size, quantity, customizations);
        orderManager.addItem(currentOrder.getId(), item);

        orderSummaryArea.appendText(
                "\n\nAdded: " + selected.getName() +
                " x" + quantity +
                " -> $" + String.format("%.2f", item.getSubtotal()) +
                "\nOrder total: $" + String.format("%.2f", currentOrder.getTotal())
        );
    }

    @FXML
    private void handleLogout() {
        if (mainController != null) {
            mainController.showLoginScreen();
        }
    }
}
