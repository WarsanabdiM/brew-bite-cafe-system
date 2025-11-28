package app.factory;

import app.model.Beverage;

public class BeverageFactory {

    public Beverage createFromTemplate(Beverage template) {
        if (template == null) {
            throw new IllegalArgumentException("Template beverage cannot be null");
        }
        return template;
    }
}
