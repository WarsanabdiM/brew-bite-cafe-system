package app.model.item;

public class Customization {

    private final String id;
    private String name;
    private double priceAdjustment;

    public Customization(String id, String name, double adjustment) {
        this.id = id;
        this.name = name;
        this.priceAdjustment = adjustment;
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public double getPriceAdjustment() { return priceAdjustment; }

    public void setPriceAdjustment(double priceAdjustment) {
        this.priceAdjustment = priceAdjustment;
    }

    @Override
    public String toString() {
        return name + " (+" + priceAdjustment + ")";
    }
}
