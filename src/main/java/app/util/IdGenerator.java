package app.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.UUID;

public class IdGenerator {
    
    // Atomic integer ensures thread safety if multiple customers order at once
    private static final AtomicInteger orderCounter = new AtomicInteger(100);

    // Generates a sequential integer ID (e.g., 101, 102) for Orders
    // Helpful for the Barista view to see FIFO order
    public static int generateOrderId() {
        return orderCounter.incrementAndGet();
    }

    // Generates a unique string ID for generic items if needed
    public static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }
}
