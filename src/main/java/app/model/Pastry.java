package app.model;

import java.util.Objects;

public class Pastry extends MenuItem {

    public Pastry(String id, String name, double basePrice) {
        super(id, name, basePrice);
    }

    @Override
    public double calculatePrice(Size size, java.util.List<Customization> customizations) {
        double total = getBasePrice();
        total += size.getPriceAdjustment();
        if (customizations != null) {
            for (Customization c : customizations) {
                total += c.getPriceAdjustment();
            }
        }
        return total;
    }
}
