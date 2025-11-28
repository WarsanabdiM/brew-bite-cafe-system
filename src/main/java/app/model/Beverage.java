package app.model;

import java.util.List;
import java.util.Map;

public class Beverage extends MenuItem {

    private final boolean iced;
    private final int caffeineMg;
    private final Map<String, Integer> recipe;

    public Beverage(String id, String name, double basePrice,
        boolean iced, int caffeineMg,
        Map<String, Integer> recipe) {
        super(id, name, basePrice);
        this.iced = iced;
        this.caffeineMg = caffeineMg;
        this.recipe = recipe;
    }

    public boolean isIced() { return iced; }
    public int getCaffeineMg() { return caffeineMg; }
    public Map<String, Integer> getRecipe() { return recipe; }

    @Override
    public double calculatePrice(Size size, List<Customization> customs) {
        double price = getBasePrice() * size.getPriceAdjustment();
        for (Customization c : customs) price += c.getPriceAdjustment();
        return price;
    }
}

