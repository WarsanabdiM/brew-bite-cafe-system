package app.model;

import java.util.Objects;

public class Customization {

    private final String id;
    private String name;
    private double priceAdjustment;

    public Customization(String id, String name, double priceAdjustment) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.priceAdjustment = priceAdjustment;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public double getPriceAdjustment() {
        return priceAdjustment;
    }

    public void setPriceAdjustment(double priceAdjustment) {
        this.priceAdjustment = priceAdjustment;
    }

    @Override
    public String toString() {
        return name + " (+" + priceAdjustment + ")";
    }
}

