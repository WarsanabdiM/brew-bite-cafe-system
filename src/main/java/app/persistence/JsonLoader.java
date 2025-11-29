package app.persistence;

import app.model.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;

public class JsonLoader {

    private static final String DATA_DIR = "src/main/resources/data/";

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    // -------------------------
    // LOAD USERS
    // -------------------------
    public static List<User> loadUsers() {
        try (FileReader reader = new FileReader(DATA_DIR + "users.json")) {
            Type listType = new TypeToken<ArrayList<User>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // -------------------------
    // LOAD INVENTORY
    // -------------------------
    public static Inventory loadInventory() {

        Inventory inventory = new Inventory();

        try (FileReader reader = new FileReader(DATA_DIR + "inventory.json")) {

            JsonArray arr = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement el : arr) {

                JsonObject obj = el.getAsJsonObject();

                String id = obj.get("id").getAsString();
                String name = obj.get("name").getAsString();
                String unit = obj.get("unit").getAsString();
                int qty = obj.get("quantity").getAsInt();

                Ingredient ing = new Ingredient(id, name, unit);
                inventory.addStock(ing, qty);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return inventory;
    }

    // -------------------------
    // LOAD MENU
    // -------------------------
    public static List<MenuItem> loadMenu() {

        List<MenuItem> menu = new ArrayList<>();

        try (FileReader reader = new FileReader(DATA_DIR + "menu.json")) {

            JsonArray arr = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement el : arr) {

                JsonObject obj = el.getAsJsonObject();

                String type = obj.get("type").getAsString();
                String id = obj.get("id").getAsString();
                String name = obj.get("name").getAsString();
                double basePrice = obj.get("basePrice").getAsDouble();

                // recipe
                Map<String, Integer> recipe = new HashMap<>();
                JsonObject rec = obj.getAsJsonObject("ingredients");
                for (String key : rec.keySet()) {
                    recipe.put(key, rec.get(key).getAsInt());
                }

                if (type.equalsIgnoreCase("BEVERAGE")) {
                    boolean iced = obj.get("iced").getAsBoolean();
                    int caffeine = obj.get("caffeineMg").getAsInt();

                    menu.add(new Beverage(id, name, basePrice, iced, caffeine, recipe));
                }
                else if (type.equalsIgnoreCase("PASTRY")) {
                    boolean heated = obj.get("heated").getAsBoolean();
                    int calories = obj.has("calories") ? obj.get("calories").getAsInt() : 0;

                    menu.add(new Pastry(id, name, basePrice, heated, calories, recipe));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return menu;
    }

    // -------------------------
    // LOAD ORDERS
    // -------------------------
    public static List<Order> loadOrders() {

        List<Order> orders = new ArrayList<>();

        try (FileReader reader = new FileReader(DATA_DIR + "orders.json")) {

            JsonArray arr = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement el : arr) {

                JsonObject obj = el.getAsJsonObject();

                String id = obj.get("id").getAsString();
                OrderStatus status = OrderStatus.valueOf(obj.get("status").getAsString());
                User user = gson.fromJson(obj.get("createdBy"), User.class);

                // CREATE ORDER USING AVAILABLE CONSTRUCTOR
                Order order = new Order(id, user);
                order.setStatus(status);

                // load items
                JsonArray itemsArr = obj.getAsJsonArray("items");

                for (JsonElement itemEl : itemsArr) {
                    JsonObject itemObj = itemEl.getAsJsonObject();

                    int quantity = itemObj.get("quantity").getAsInt();
                    Size size = Size.valueOf(itemObj.get("size").getAsString());

                    // menu item object
                    JsonObject mObj = itemObj.getAsJsonObject("menuItem");
                    String type = mObj.get("type").getAsString();

                    MenuItem mi;

                    if (type.equals("BEVERAGE")) {
                        mi = new Beverage(
                                mObj.get("id").getAsString(),
                                mObj.get("name").getAsString(),
                                mObj.get("basePrice").getAsDouble(),
                                mObj.get("iced").getAsBoolean(),
                                mObj.get("caffeineMg").getAsInt(),
                                new HashMap<>()
                        );
                    } else {
                        mi = new Pastry(
                                mObj.get("id").getAsString(),
                                mObj.get("name").getAsString(),
                                mObj.get("basePrice").getAsDouble(),
                                mObj.get("heated").getAsBoolean(),
                                mObj.has("calories") ? mObj.get("calories").getAsInt() : 0,
                                new HashMap<>()
                        );
                    }

                    List<Customization> customs =
                            gson.fromJson(itemObj.get("customizations"),
                                    new TypeToken<List<Customization>>(){}.getType());

                    order.addItem(new OrderItem(mi, size, quantity, customs));
                }

                orders.add(order);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return orders;
    }
}
