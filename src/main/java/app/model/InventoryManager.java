package app.model;

import java.util.Map;

public class InventoryManager {

    private final Inventory inventory;

    public InventoryManager(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void restock(Ingredient ing, int qty) {
        inventory.addStock(ing, qty);
    }

    public boolean consume(Ingredient ing, int qty) {
        return inventory.removeStock(ing, qty);
    }

    
    public boolean consumeIngredients(Order order) {
        for (OrderItem item : order.getItems()) {
            MenuItem menuItem = item.getMenuItem();

            Map<String, Integer> recipe = null;

            if (menuItem instanceof Beverage b) recipe = b.getRecipe();
            else if (menuItem instanceof Pastry p) recipe = p.getRecipe();

            if (recipe == null) continue;

            
            for (var entry : recipe.entrySet()) {
                Ingredient ing = new Ingredient(entry.getKey(), entry.getKey(), "units");
                int needed = entry.getValue() * item.getQuantity();

                if (inventory.getQuantity(ing) < needed) return false;
            }

            
            for (var entry : recipe.entrySet()) {
                Ingredient ing = new Ingredient(entry.getKey(), entry.getKey(), "units");
                int needed = entry.getValue() * item.getQuantity();
                inventory.removeStock(ing, needed);
            }
        }
        return true;
    }
}
