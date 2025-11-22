package app.model;

import java.util.List;

public class Beverage extends MenuItem {

    private final boolean iced;
    private final int caffeineMg;

    public Beverage(String id, String name, double basePrice,
                    boolean iced, int caffeineMg) {
        super(id, name, basePrice);
        this.iced = iced;
        this.caffeineMg = caffeineMg;
    }

    public boolean isIced() { return iced; }
    public int getCaffeineMg() { return caffeineMg; }

    @Override
    public double calculatePrice(Size size, List<Customization> customs) {
        double total = getBasePrice() * size.getPriceAdjustment();
        if (customs != null) {
            for (Customization c : customs) {
                total += c.getPriceAdjustment();
            }
        }
        return total;
    }
}
