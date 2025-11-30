package app.model.inventory;

import app.model.ingredient.Ingredient;
import app.model.ingredient.Inventory;
import app.model.item.MenuItem;
import app.model.observer.Observable;
import app.model.order.Order;
import app.model.order.OrderItem;
import java.util.Map;

public class InventoryManager extends Observable {

    private final Inventory inventory;

    public InventoryManager(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }



    public void restock(Ingredient ingredient, int qty) {
        inventory.addStock(ingredient, qty);
        notifyObservers();
    }

    public boolean hasIngredientsFor(Order order) {
        for (OrderItem item : order.getItems()) {
            MenuItem menuItem = item.getMenuItem();

            Map<String, Integer> recipe = menuItem.getRecipe();  
            if (recipe == null) continue;

            for (var entry : recipe.entrySet()) {
                String ingId = entry.getKey();
                int needed = entry.getValue() * item.getQuantity();

                Ingredient ingredient = new Ingredient(ingId, ingId, "units");
                if (inventory.getQuantity(ingredient) < needed) return false;
            }
        }
        return true;
    }

    public void consumeIngredients(Order order) {
        for (OrderItem item : order.getItems()) {
            MenuItem menuItem = item.getMenuItem();

            Map<String, Integer> recipe = menuItem.getRecipe();
            if (recipe == null) continue;

            for (var entry : recipe.entrySet()) {
                String ingId = entry.getKey();
                int needed = entry.getValue() * item.getQuantity();

                Ingredient ingredient = new Ingredient(ingId, ingId, "units");
                inventory.removeStock(ingredient, needed);
            }
        }
        notifyObservers();
    }
}