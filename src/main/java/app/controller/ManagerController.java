package app.controller;

import app.factory.BeverageFactory;
import app.factory.PastryFactory;
import app.model.Beverage;
import app.model.Ingredient;
import app.model.Inventory;
import app.model.InventoryManager;
import app.model.MenuItem;
import app.model.MenuManager;
import app.model.Pastry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;

public class ManagerController {

    private MainController mainController;
    private MenuManager menuManager;
    private InventoryManager inventoryManager;

    private final BeverageFactory beverageFactory = new BeverageFactory();
    private final PastryFactory pastryFactory = new PastryFactory();

    private final ObservableList<MenuItem> menuItems =
            FXCollections.observableArrayList();

    private final ObservableList<Ingredient> ingredientItems =
            FXCollections.observableArrayList();

    @FXML
    private TableView<MenuItem> menuTable;

    @FXML
    private TextField menuNameField;

    @FXML
    private TextField menuBasePriceField;

    @FXML
    private ComboBox<String> menuTypeCombo;

    @FXML
    private TextArea ingredientsTextArea;

    @FXML
    private TableView<Ingredient> inventoryTable;

    @FXML
    private TextField ingredientNameField;

    @FXML
    private TextField ingredientUnitField;

    @FXML
    private TextField ingredientQtyField;

    @FXML
    private Button addIngredientButton;

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

    @FXML
    private void initialize() {
        if (menuTable != null) {
            menuTable.setItems(menuItems);
        }
        if (inventoryTable != null) {
            inventoryTable.setItems(ingredientItems);
        }

        if (menuTypeCombo != null) {
            menuTypeCombo.getItems().setAll("Beverage", "Pastry");
            menuTypeCombo.getSelectionModel().selectFirst();
        }
    }

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
        String type = menuTypeCombo != null ? menuTypeCombo.getValue() : "Beverage";
        Map<String, Integer> recipe = parseIngredientsText();

        MenuItem item;
        if ("Pastry".equalsIgnoreCase(type)) {
            boolean canBeWarmed = true;
            int calories = 300;
            Pastry template = new Pastry(id, name, basePrice, canBeWarmed, calories, recipe);
            item = pastryFactory.createFromTemplate(template);
        } else {
            boolean iced = false;
            int caffeineMg = 80;
            Beverage template = new Beverage(id, name, basePrice, iced, caffeineMg, recipe);
            item = beverageFactory.createFromTemplate(template);
        }

        if (menuManager != null) {
            menuManager.addMenuItem(item);
        }
        menuItems.add(item);

        menuNameField.clear();
        menuBasePriceField.clear();
        if (ingredientsTextArea != null) {
            ingredientsTextArea.clear();
        }
    }

    private Map<String, Integer> parseIngredientsText() {
        Map<String, Integer> map = new HashMap<>();
        if (ingredientsTextArea == null) {
            return map;
        }
        String text = ingredientsTextArea.getText();
        if (text == null || text.isBlank()) {
            return map;
        }
        String[] lines = text.split("\\r?\\n");
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;
            String[] parts = trimmed.split(":");
            if (parts.length != 2) continue;
            String name = parts[0].trim();
            String qtyStr = parts[1].trim();
            try {
                int qty = Integer.parseInt(qtyStr);
                if (qty > 0) {
                    map.put(name, qty);
                }
            } catch (NumberFormatException ignored) {
            }
        }
        return map;
    }

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

        String id = "I" + (ingredientItems.size() + 1);
        Ingredient ingredient = new Ingredient(id, name, unit);

        inventoryManager.restock(ingredient, quantity);
        refreshInventoryList();

        ingredientNameField.clear();
        ingredientUnitField.clear();
        ingredientQtyField.clear();
    }

    private void refreshInventoryList() {
        Inventory inv = inventoryManager.getInventory();
        Map<Ingredient, Integer> snapshot = inv.getSnapshot();
        ingredientItems.setAll(snapshot.keySet());
    }

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
