package app.model.order;

import app.model.item.Customization;
import app.model.item.MenuItem;
import app.model.item.Size;
import java.util.Collections;
import java.util.List;


public class OrderItem {

    private final MenuItem menuItem;
    private final Size size;
    private final int quantity;
    private final List<Customization> customizations;

    public OrderItem(MenuItem item, Size size, int qty, List<Customization> customs) {
        this.menuItem = item;
        this.size = size;
        this.quantity = qty;
        this.customizations = customs;
    }

    public MenuItem getMenuItem() { return menuItem; }

    public Size getSize() { return size; }

    public int getQuantity() { return quantity; }

    public List<Customization> getCustomizations() {
        return Collections.unmodifiableList(customizations);
    }

    public double getSubtotal() {
        return menuItem.calculatePrice(size, customizations) * quantity;
    }
}