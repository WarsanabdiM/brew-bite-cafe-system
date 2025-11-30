package app.factory;

import app.model.item.Beverage;
import java.util.HashMap;

public class BeverageFactory {

    public Beverage create(String id,
                           String name,
                           double basePrice,
                           boolean iced,
                           int caffeineMg,
                           java.util.Map<String, Integer> recipe) {

        return new Beverage(
                id,
                name,
                basePrice,
                iced,
                caffeineMg,
                new HashMap<>(recipe)   // deep copy
        );
    }

    public Beverage clone(Beverage template) {
        return new Beverage(
                template.getId(),
                template.getName(),
                template.getBasePrice(),
                template.isIced(),
                template.getCaffeineMg(),
                new HashMap<>(template.getRecipe())
        );
    }
}