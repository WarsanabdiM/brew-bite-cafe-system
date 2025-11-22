package app.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Inventory {

    private final Map<Ingredient, Integer> stock = new HashMap<>();

    public int getQuantity(Ingredient ingredient) {
        return stock.getOrDefault(ingredient, 0);
    }

    public Map<Ingredient, Integer> getSnapshot() {
        return Collections.unmodifiableMap(stock);
    }

    public void addStock(Ingredient ingredient, int quantity) {
        Objects.requireNonNull(ingredient);
        if (quantity <= 0) return;
        stock.merge(ingredient, quantity, Integer::sum);
    }

    /**
     * Attempts to remove quantity from stock.
     *
     * @return true if enough stock existed and was removed, false otherwise.
     */
    public boolean removeStock(Ingredient ingredient, int quantity) {
        Objects.requireNonNull(ingredient);
        if (quantity <= 0) return true;

        int current = stock.getOrDefault(ingredient, 0);
        if (current < quantity) {
            return false;
        }
        int remaining = current - quantity;
        if (remaining == 0) {
            stock.remove(ingredient);
        } else {
            stock.put(ingredient, remaining);
        }
        return true;
    }
}
