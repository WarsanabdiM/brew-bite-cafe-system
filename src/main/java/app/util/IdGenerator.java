package app.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.UUID;

public class IdGenerator {
    
   
    private static final AtomicInteger orderCounter = new AtomicInteger(100);

   
    public static int generateOrderId() {
        return orderCounter.incrementAndGet();
    }

   
    public static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }
}
