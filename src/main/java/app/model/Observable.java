package app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Observable {

    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(Objects.requireNonNull(observer));
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    protected void notifyObservers() {
        for (Observer o : new ArrayList<>(observers)) { // copy to avoid concurrent modification
            o.update(this);
        }
    }
}
