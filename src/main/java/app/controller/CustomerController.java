package app.controller;

import app.model.item.MenuItem;
import app.model.menu.MenuManager;
import app.model.order.Order;
import app.model.order.OrderItem;
import app.model.order.OrderManager;
import app.model.order.Size;
import app.model.user.User;
import app.model.observer.Observer;
import app.model.observer.Observable;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.util.Collections;

public class CustomerController implements Observer {

    private final MainController main;
    private final MenuManager menuManager;
    private final OrderManager orderManager;
    private final User user;

    private Order currentOrder;

    @FXML private TableView<MenuItem> menuTable;
    @FXML private TextArea orderSummary;

    public CustomerController(MainController main,
                              MenuManager menuManager,
                              OrderManager orderManager,
                              User user) {

        this.main = main;
        this.menuManager = menuManager;
        this.orderManager = orderManager;
        this.user = user;

        menuManager.addObserver(this);

        currentOrder = orderManager.createOrder(user);
    }

    @FXML
    private void initialize() {
        menuTable.getItems().setAll(menuManager.getAll());
    }

    @FXML
    private void handlePlaceOrder() {
        MenuItem selected = menuTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        OrderItem item = new OrderItem(
                selected,
                Size.SMALL,
                1,
                Collections.emptyList()
        );

        orderManager.addItem(currentOrder, item);
        refreshSummary();
    }

    @FXML
    private void handleLogout() {
        main.showLogin();
    }

    @Override
    public void update(Observable o) {
        menuTable.getItems().setAll(menuManager.getAll());
    }

    private void refreshSummary() {
        StringBuilder sb = new StringBuilder();

        sb.append("Order ID: ").append(currentOrder.getId()).append("\n\n");
        currentOrder.getItems()
                .forEach(i -> sb.append(i.getMenuItem().getName())
                        .append(" x")
                        .append(i.getQuantity())
                        .append(" -> $")
                        .append(String.format("%.2f", i.getSubtotal()))
                        .append("\n"));

        sb.append("\nTotal: $").append(
                String.format("%.2f", currentOrder.getTotal())
        );

        orderSummary.setText(sb.toString());
    }
}
