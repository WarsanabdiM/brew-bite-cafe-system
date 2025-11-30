package app.model.item;

import java.util.List;

public abstract class MenuItem {

    private final String id;
    private String name;
    private double basePrice;

    protected MenuItem(String id, String name, double basePrice) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public double getBasePrice() { return basePrice; }

    public void setBasePrice(double price) { this.basePrice = price; }

    public abstract double calculatePrice(Size size, List<Customization> customizations);

    public abstract java.util.Map<String, Integer> getRecipe();

    @Override
    public String toString() {
        return name + " ($" + basePrice + ")";
    }
}
