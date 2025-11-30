package app.model.item;

import java.util.List;
import java.util.Map;

public class Pastry extends MenuItem {

    private final boolean canBeWarmed;
    private final int calories;
    private final Map<String, Integer> recipe;

    public Pastry(String id, String name, double basePrice,
        boolean canBeWarmed, int calories, Map<String, Integer> recipe) {
        super(id, name, basePrice);
        this.canBeWarmed = canBeWarmed;
        this.calories = calories;
        this.recipe = recipe;
    }

    public boolean canBeWarmed() { return canBeWarmed; }
    public int getCalories() { return calories; }

    @Override
    public Map<String, Integer> getRecipe() {
        return recipe;
    }

    @Override
    public double calculatePrice(Size size, List<Customization> customizations) {
        double price = getBasePrice();
        for (Customization c : customizations)
            price += c.getPriceAdjustment();
        return price;
    }
}