package app.factory;

import app.model.item.Pastry;
import java.util.HashMap;

public class PastryFactory {

    public Pastry create(String id,
                         String name,
                         double basePrice,
                         boolean canBeWarmed,
                         int calories,
                         java.util.Map<String, Integer> recipe) {

        return new Pastry(
                id,
                name,
                basePrice,
                canBeWarmed,
                calories,
                new HashMap<>(recipe)   // deep copy
        );
    }

    public Pastry clone(Pastry template) {
        return new Pastry(
                template.getId(),
                template.getName(),
                template.getBasePrice(),
                template.canBeWarmed(),
                template.getCalories(),
                new HashMap<>(template.getRecipe())
        );
    }
}