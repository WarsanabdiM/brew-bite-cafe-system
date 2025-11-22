package app.model;

public enum Size {
    SMALL(0.0),
    MEDIUM(0.75),
    LARGE(1.25);

    private final double priceAdjustment;

    Size(double priceAdjustment) {
        this.priceAdjustment = priceAdjustment;
    }

    public double getPriceAdjustment() {
        return priceAdjustment;
    }
}
