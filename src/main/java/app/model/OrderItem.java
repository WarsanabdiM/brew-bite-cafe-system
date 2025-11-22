package app.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class OrderItem {

    private final MenuItem menuItem;
    private final Size size;
    private final int quantity;
    private final List<Customization> customizations;

    public OrderItem(MenuItem menuItem, Size size, int quantity,
        List<Customization> customizations) {
        this.menuItem = Objects.requireNonNull(menuItem);
        this.size = Objects.requireNonNull(size);
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.quantity = quantity;
        if (customizations == null) {
            this.customizations = new ArrayList<>();
        } else {
            this.customizations = new ArrayList<>(customizations);
        }
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public Size getSize() {
        return size;
    }

    public int getQuantity() {
        return quantity;
    }

    public List<Customization> getCustomizations() {
        return Collections.unmodifiableList(customizations);
    }

    public double getSubtotal() {
        double unitPrice = menuItem.calculatePrice(size, customizations);
        return unitPrice * quantity;
    }
}
