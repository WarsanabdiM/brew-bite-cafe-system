package app.persistence;

import app.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class JsonSaver {

    private static final String DATA_DIR = "src/main/resources/data/";

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();


    public static void saveInventory(Inventory inventory) {

        List<Map<String, Object>> out = new ArrayList<>();

        for (Map.Entry<Ingredient, Integer> entry : inventory.getSnapshot().entrySet()) {

            Ingredient ing = entry.getKey();
            int qty = entry.getValue();

            Map<String, Object> obj = new HashMap<>();
            obj.put("id", ing.getId());
            obj.put("name", ing.getName());
            obj.put("quantity", qty);
            obj.put("unit", ing.getUnit());

            out.add(obj);
        }

        try (FileWriter writer = new FileWriter(DATA_DIR + "inventory.json")) {
            gson.toJson(out, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
