package app.persistence;

import app.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonSaver {

    private static final String DATA_DIR = "src/main/resources/data/";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveUsers(List<User> users) {
        saveToFile(users, "users.json");
    }

    public static void saveMenu(List<MenuItem> menu) {
        saveToFile(menu, "menu.json");
    }

    public static void saveOrders(List<Order> orders) {
        saveToFile(orders, "orders.json");
    }

    public static void saveInventory(Inventory inventory) {
        Map<Ingredient, Integer> stock = inventory.getSnapshot();
        for (Map.Entry<Ingredient, Integer> entry : stock.entrySet()) {
       }
        saveToFile(stock.keySet(), "inventory.json");
    }

    private static void saveToFile(Object data, String filename) {
        try (FileWriter writer = new FileWriter(DATA_DIR + filename)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}