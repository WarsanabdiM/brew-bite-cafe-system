package app.model.order;

import app.model.observer.Observable;
import app.model.user.User;
import app.util.IdGenerator;
import java.util.ArrayList;
import java.util.List;

public class OrderManager extends Observable {

    private final List<Order> orders = new ArrayList<>();

    public Order createOrder(User user) {
        Order order = new Order(IdGenerator.generateOrderId(), user);
        orders.add(order);
        notifyObservers();
        return order;
    }

    public void addItem(Order order, OrderItem item) {
        order.addItem(item);
        notifyObservers();
    }

    public List<Order> getActiveOrders() {
        return orders.stream()
        .filter(o -> o.getStatus() != OrderStatus.COMPLETED)
        .toList();
    }

    public void markInProgress(Order order) {
        order.setStatus(OrderStatus.IN_PROGRESS);
        notifyObservers();
    }

    public void markReady(Order order) {
        order.setStatus(OrderStatus.READY);
        notifyObservers();
    }

    public void markCompleted(Order order) {
        order.setStatus(OrderStatus.COMPLETED);
        notifyObservers();
    }
}