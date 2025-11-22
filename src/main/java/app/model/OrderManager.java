package app.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class OrderManager extends Observable {

    private final List<Order> orders = new ArrayList<>();

    public Order createOrder(User user) {
        Objects.requireNonNull(user);
        String id = "ORD-" + (orders.size() + 1);
        Order order = new Order(id, user);
        orders.add(order);
        notifyObservers();
        return order;
    }

    public void addItem(String orderId, OrderItem item) {
        Order order = findById(orderId);
        if (order != null) {
            order.addItem(item);
            notifyObservers();
        }
    }

    public void updateStatus(String orderId, OrderStatus status) {
        Order order = findById(orderId);
        if (order != null) {
            order.setStatus(status);
            notifyObservers();
        }
    }

    public List<Order> getActiveOrders() {
        List<Order> active = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus() == OrderStatus.NEW
                    || order.getStatus() == OrderStatus.IN_PROGRESS) {
                active.add(order);
            }
        }
        return Collections.unmodifiableList(active);
    }

    public List<Order> getAllOrders() {
        return Collections.unmodifiableList(orders);
    }

    private Order findById(String orderId) {
        for (Order order : orders) {
            if (order.getId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }
}
