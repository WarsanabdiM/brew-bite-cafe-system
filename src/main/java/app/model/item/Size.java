package app.model.item;

public enum Size {
    SMALL(1.0),
    MEDIUM(1.25),
    LARGE(1.5);

    private final double multiplier;

    Size(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getPriceAdjustment() {
        return multiplier;
    }
}