package app.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MenuManager {

    private final List<MenuItem> menuItems = new ArrayList<>();

    public void addMenuItem(MenuItem item) {
        menuItems.add(Objects.requireNonNull(item));
    }

    public List<MenuItem> getAll() {
        return Collections.unmodifiableList(menuItems);
    }

    public MenuItem findById(String id) {
        for (MenuItem item : menuItems) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }
}
