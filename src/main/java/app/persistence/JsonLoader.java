package app.persistence;

import app.model.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            
            for (JsonElement element : array) {
                JsonObject obj = element.getAsJsonObject();
                String type = obj.get("type").getAsString(); 

                if ("BEVERAGE".equalsIgnoreCase(type)) {
                    menu.add(gson.fromJson(obj, Beverage.class));
                } else if ("PASTRY".equalsIgnoreCase(type)) {
                    menu.add(gson.fromJson(obj, Pastry.class));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return menu;
    }


    public static List<Order> loadOrders() {
        return new ArrayList<>(); 
    }
}