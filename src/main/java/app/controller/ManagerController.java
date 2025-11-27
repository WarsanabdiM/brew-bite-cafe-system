package app.controller;

import app.model.Beverage;
import app.model.Ingredient;
import app.model.Inventory;
import app.model.InventoryManager;
import app.model.MenuItem;
import app.model.MenuManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Map;

/**
 * Manager screen:
 *  - manages menu items
 *  - manages inventory ingredients.
 *
 
 *  - MenuItem is abstract
 *  - Beverage extends MenuItem
 *  - Ingredient has (id, name, unit)
 *  - InventoryManager wraps an Inventory with quantities
 */
public class ManagerController {

    private MainController mainController;
    private MenuManager menuManager;
    private InventoryManager inventoryManager;

    private final ObservableList<MenuItem> menuItems =
            FXCollections.observableArrayList();

    private final ObservableList<Ingredient> ingredientItems =
            FXCollections.observableArrayList();

    /* ========= MENU UI (manager_menu.fxml) ========= */

    // Table of menu items
    @FXML
    private TableView<MenuItem> menuTable;      // fx:id="menuTable"

    // Form fields for a new menu item
    @FXML
    private TextField menuNameField;            // fx:id="menuNameField"

    @FXML
    private TextField menuBasePriceField;       // fx:id="menuBasePriceField"

    
    @FXML
    private ComboBox<String> menuTypeCombo;     // fx:id="menuTypeCombo"

    
    @FXML
    private TextArea ingredientsTextArea;       // fx:id="ingredientsTextArea"

    /* ========= INVENTORY UI (manager_inventory.fxml) ========= */

    @FXML
    private TableView<Ingredient> inventoryTable;   // fx:id="inventoryTable"

    @FXML
    private TextField ingredientNameField;          // fx:id="ingredientNameField"

    @FXML
    private TextField ingredientUnitField;          // fx:id="ingredientUnitField"

    @FXML
    private TextField ingredientQtyField;           // fx:id="ingredientQtyField"

    @FXML
    private Button addIngredientButton;             // fx:id="addIngredientButton"


    /* ========= Wiring from MainController ========= */

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
        if (menuManager != null) {
            menuItems.setAll(menuManager.getAll());
        }
    }

    public void setInventoryManager(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
        if (inventoryManager != null) {
            refreshInventoryList();
        }
    }

    /* ========= JavaFX lifecycle ========= */

    @FXML
    private void initialize() {
        
        if (menuTable != null) {
            menuTable.setItems(menuItems);
        }
        if (inventoryTable != null) {
            inventoryTable.setItems(ingredientItems);
        }

        // Optional type combo
        if (menuTypeCombo != null) {
            menuTypeCombo.getItems().setAll("Beverage");
            menuTypeCombo.getSelectionModel().selectFirst();
        }
    }

    /* ========= MENU ACTIONS ========= */

    @FXML
    private void handleAddMenuItem() {
        if (menuNameField == null || menuBasePriceField == null) {
        
            return;
        }

        String name = menuNameField.getText();
        String priceText = menuBasePriceField.getText();

        if (name == null || name.isBlank()
                || priceText == null || priceText.isBlank()) {
            return;
        }

        double basePrice;
        try {
            basePrice = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            
            return;
        }

        
        String id = "M" + (menuItems.size() + 1);

        
        MenuItem item = new Beverage(id, name, basePrice, false, 0);

        // persist in the in-memory menu
        if (menuManager != null) {
            menuManager.addMenuItem(item);
        }
        menuItems.add(item);

        // clear form
        menuNameField.clear();
        menuBasePriceField.clear();
        if (ingredientsTextArea != null) {
            ingredientsTextArea.clear();
        }
    }

    /* ========= INVENTORY ACTIONS ========= */

    @FXML
    private void handleAddIngredient() {
        if (ingredientNameField == null
                || ingredientUnitField == null
                || ingredientQtyField == null
                || inventoryManager == null) {
            
            return;
        }

        String name = ingredientNameField.getText();
        String unit = ingredientUnitField.getText();
        String qtyText = ingredientQtyField.getText();

        if (name == null || name.isBlank()
                || unit == null || unit.isBlank()
                || qtyText == null || qtyText.isBlank()) {
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(qtyText);
        } catch (NumberFormatException e) {
            return;
        }

        if (quantity <= 0) {
            return;
        }

        // Generate a simple ingredient ID
        String id = "I" + (ingredientItems.size() + 1);

        // Ingredient constructor is (id, name, unit)
        Ingredient ingredient = new Ingredient(id, name, unit);

        // Add to inventory with the starting quantity
        inventoryManager.restock(ingredient, quantity);

        // Refresh the list from the Inventory snapshot
        refreshInventoryList();

        // clear fields
        ingredientNameField.clear();
        ingredientUnitField.clear();
        ingredientQtyField.clear();
    }

    private void refreshInventoryList() {
        Inventory inv = inventoryManager.getInventory();
        Map<Ingredient, Integer> snapshot = inv.getSnapshot();
        ingredientItems.setAll(snapshot.keySet());
    }

    /* ========= NAVIGATION BUTTONS ========= */

    @FXML
    private void handleGoToInventory() {
        if (mainController != null) {
            mainController.showManagerInventoryScreen();
        }
    }

    @FXML
    private void handleGoToMenu() {
        if (mainController != null) {
            mainController.showManagerMenuScreen();
        }
    }

    @FXML
    private void handleLogout() {
        if (mainController != null) {
            mainController.showLoginScreen();
        }
    }
}
