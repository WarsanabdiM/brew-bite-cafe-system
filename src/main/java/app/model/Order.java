package app.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Order {

    private final String id;
    private final LocalDateTime createdAt;
    private OrderStatus status;
    private final List<OrderItem> items = new ArrayList<>();
    private final User createdBy;

    public Order(String id, User createdBy) {
        this.id = Objects.requireNonNull(id);
        this.createdBy = Objects.requireNonNull(createdBy);
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.NEW;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = Objects.requireNonNull(status);
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void addItem(OrderItem item) {
        items.add(Objects.requireNonNull(item));
    }

    public double getTotal() {
        return items.stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();
    }
}
