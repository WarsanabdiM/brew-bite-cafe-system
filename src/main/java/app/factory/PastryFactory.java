package app.factory;

import app.model.Pastry;

public class PastryFactory {

    public Pastry createFromTemplate(Pastry template) {
        if (template == null) {
            throw new IllegalArgumentException("Template pastry cannot be null");
        }
        return template;
    }
}
