package app.model.menu;

import app.model.item.MenuItem;
import app.model.observer.Observable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuManager extends Observable {

    private final List<MenuItem> items = new ArrayList<>();

    public void addMenuItem(MenuItem item) {
        items.add(item);
        notifyObservers();
    }

    public List<MenuItem> getAll() {
        return Collections.unmodifiableList(items);
    }

    public MenuItem findById(String id) {
        return items.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}