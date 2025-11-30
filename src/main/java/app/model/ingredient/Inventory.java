package app.model.ingredient;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private final Map<Ingredient, Integer> stock = new HashMap<>();

    public int getQuantity(Ingredient ingredient) {
        return stock.getOrDefault(ingredient, 0);
    }

    public Map<Ingredient, Integer> getSnapshot() {
        return Collections.unmodifiableMap(stock);
    }

    public void addStock(Ingredient ingredient, int quantity) {
        if (quantity <= 0) return;
        stock.merge(ingredient, quantity, Integer::sum);
    }

    public boolean removeStock(Ingredient ingredient, int quantity) {
        if (quantity <= 0) return true;

        int current = stock.getOrDefault(ingredient, 0);
        if (current < quantity) return false;

        int remaining = current - quantity;
        if (remaining == 0) stock.remove(ingredient);
        else stock.put(ingredient, remaining);

        return true;
    }
}