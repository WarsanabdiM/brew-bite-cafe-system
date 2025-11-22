package app.model;

import java.util.Objects;

public class InventoryManager extends Observable {

    private final Inventory inventory;

    public InventoryManager(Inventory inventory) {
        this.inventory = Objects.requireNonNull(inventory);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void restock(Ingredient ingredient, int quantity) {
        inventory.addStock(ingredient, quantity);
        notifyObservers();
    }

    public boolean consume(Ingredient ingredient, int quantity) {
        boolean success = inventory.removeStock(ingredient, quantity);
        if (success) {
            notifyObservers();
        }
        return success;
    }
}
