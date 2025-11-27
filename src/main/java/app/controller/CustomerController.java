package app.controller;

import app.model.MenuItem;
import app.model.MenuManager;
import app.model.OrderManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

/**
 * Customer-facing UI:
 * - shows menu items
 * - lets customer see a simple order summary.
 */
public class CustomerController {

    private MainController mainController;
    private MenuManager menuManager;
    private OrderManager orderManager;

    private final ObservableList<MenuItem> menuItems =
            FXCollections.observableArrayList();

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
    }

    public void setOrderManager(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    @FXML
    private void initialize() {
        if (menuTable != null) {
            menuTable.setItems(menuItems);
        }

        // TODO: When MenuManager API is finalized, load real menu items, e.g.:
        // if (menuManager != null) {
        //     menuItems.setAll(menuManager.getAllMenuItems());
        // }
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

        orderSummaryArea.setText("Selected item:\n" + selected.toString());
    }

    @FXML
    private void handlePlaceOrder() {
        if (orderSummaryArea == null) {
            return;
        }

        // TODO: integrate with OrderManager (create Order, add OrderItem, submit).
        orderSummaryArea.appendText("\n\nOrder placed. (Connect to model later.)");
    }

    @FXML
    private void handleLogout() {
        if (mainController != null) {
            mainController.showLoginScreen();
        }
    }
}
