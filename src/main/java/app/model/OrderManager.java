package app.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class OrderManager {

    private final List<Order> orders = new ArrayList<>();

    // Removed @Override because this method is not overriding anything
    public Order createOrder(User user) {
        String id = UUID.randomUUID().toString();   // generate unique id

        // Use the constructor that actually exists: Order(String id, User createdBy)
        Order order = new Order(id, user);

        orders.add(order);
        return order;
    }

    public void addItem(String orderId, OrderItem item) {
        Order o = findById(orderId);
        if (o != null) o.addItem(item);
    }

    public void updateStatus(String orderId, OrderStatus status) {
        Order o = findById(orderId);
        if (o != null) o.setStatus(status);
    }

    public List<Order> getActiveOrders() {
        List<Order> active = new ArrayList<>();
        for (Order o : orders) {
            // Treat COMPLETED and CANCELLED as not active
            if (o.getStatus() != OrderStatus.COMPLETED &&
                o.getStatus() != OrderStatus.CANCELLED) {
                active.add(o);
            }
        }
        return Collections.unmodifiableList(active);
    }

    private Order findById(String id) {
        for (Order o : orders)
            if (o.getId().equals(id)) return o;
        return null;
    }

    // Order-fulfillment methods the controllers need
    public void markPrepared(Order order) {
        order.setStatus(OrderStatus.IN_PROGRESS);
    }

    public void markCompleted(Order order) {
        order.setStatus(OrderStatus.COMPLETED);
    }

    // Optional: if you ever want to cancel an order
    public void cancelOrder(Order order) {
        order.setStatus(OrderStatus.CANCELLED);
    }
}

