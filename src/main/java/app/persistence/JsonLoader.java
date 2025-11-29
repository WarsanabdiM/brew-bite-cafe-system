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


    public static List<User> loadUsers() {
        try (FileReader reader = new FileReader(DATA_DIR + "users.json")) {
            Type listType = new TypeToken<ArrayList<User>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static Inventory loadInventory() {
        Inventory inventory = new Inventory();

        try (FileReader reader = new FileReader(DATA_DIR + "inventory.json")) {
            Type listType = new TypeToken<ArrayList<Ingredient>>(){}.getType();
            List<Ingredient> ingredients = gson.fromJson(reader, listType);

            for (Ingredient ing : ingredients) {
                inventory.addStock(ing, ing.getQuantity());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return inventory;
    }


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

                Map<String, Integer> recipe = new HashMap<>();
                JsonObject ingredientsObj = obj.get("ingredients").getAsJsonObject();
                for (String key : ingredientsObj.keySet()) {
                    recipe.put(key, ingredientsObj.get(key).getAsInt());
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


    public static List<Order> loadOrders() {
        List<Order> orders = new ArrayList<>();

        try (FileReader reader = new FileReader(DATA_DIR + "orders.json")) {

            JsonArray arr = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement el : arr) {
                JsonObject obj = el.getAsJsonObject();

                String id = obj.get("id").getAsString();
                LocalDateTime createdAt = LocalDateTime.parse(obj.get("createdAt").getAsString());
                OrderStatus status = OrderStatus.valueOf(obj.get("status").getAsString());

                
                User user = gson.fromJson(obj.get("createdBy"), User.class);

                Order order = new Order(id, createdAt, status, user);

                
                JsonArray itemsArr = obj.getAsJsonArray("items");

                for (JsonElement itemEl : itemsArr) {
                    JsonObject itemObj = itemEl.getAsJsonObject();

                    int quantity = itemObj.get("quantity").getAsInt();
                    Size size = Size.valueOf(itemObj.get("size").getAsString());

                    
                    JsonObject menuObj = itemObj.getAsJsonObject("menuItem");
                    String type = menuObj.get("type").getAsString();

                    MenuItem menuItem;

                    if (type.equals("BEVERAGE")) {
                        boolean iced = menuObj.get("iced").getAsBoolean();
                        int caffeine = menuObj.get("caffeineMg").getAsInt();

                        menuItem = new Beverage(
                                menuObj.get("id").getAsString(),
                                menuObj.get("name").getAsString(),
                                menuObj.get("basePrice").getAsDouble(),
                                iced, caffeine,
                                new HashMap<>()
                        );
                    } else {
                        boolean heated = menuObj.get("heated").getAsBoolean();

                        menuItem = new Pastry(
                                menuObj.get("id").getAsString(),
                                menuObj.get("name").getAsString(),
                                menuObj.get("basePrice").getAsDouble(),
                                heated,
                                0,
                                new HashMap<>()
                        );
                    }

                    List<Customization> customs =
                            gson.fromJson(itemObj.get("customizations"),
                                    new TypeToken<List<Customization>>(){}.getType());

                    OrderItem oi = new OrderItem(menuItem, size, quantity, customs);
                    order.addItem(oi);
                }

                orders.add(order);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return orders;
    }
}
