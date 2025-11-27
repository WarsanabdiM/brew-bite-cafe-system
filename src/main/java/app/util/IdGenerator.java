package app.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.UUID;

public class IdGenerator {
    private static final AtomicInteger orderCounter = new AtomicInteger(1000);


    public static String generateOrderId() {
        return "ORD-" + orderCounter.incrementAndGet();
    }


    public static String generateUniqueId() {
        return UUID.randomUUID().toString().substring(0, 8); 
    }
}