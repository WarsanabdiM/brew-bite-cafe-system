package app.model;

import java.util.List;

public abstract class MenuItem {
    private final String id;
    private String name;
    private double basePrice;
    private String type;


    protected MenuItem(String id, String name, double basePrice) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }

    public abstract double calculatePrice(Size size, List<Customization> customizations);

    @Override
    public String toString() {
        return name + " ($" + basePrice + ")";
    }
}

