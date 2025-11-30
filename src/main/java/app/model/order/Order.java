package app.model.order;

import app.model.user.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {

    private final String id;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private OrderStatus status = OrderStatus.NEW;
    private final List<OrderItem> items = new ArrayList<>();
    private final User createdBy;

    public Order(String id, User createdBy) {
        this.id = id;
        this.createdBy = createdBy;
    }

    public String getId() { return id; }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public OrderStatus getStatus() { return status; }

    public void setStatus(OrderStatus status) { this.status = status; }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addItem(OrderItem item) { items.add(item); }

    public User getCreatedBy() { return createdBy; }

    public double getTotal() {
        return items.stream()
        .mapToDouble(OrderItem::getSubtotal)
        .sum();
    }
}