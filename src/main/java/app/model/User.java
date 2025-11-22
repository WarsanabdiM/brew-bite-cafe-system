package app.model;

import java.util.Objects;

public class User implements Observer {

    private final String id;
    private String name;
    private String role; // e.g., "Barista", "Manager", "Cashier"

    public User(String id, String name, String role) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.role = Objects.requireNonNull(role);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = Objects.requireNonNull(role);
    }

    @Override
    public void update(Observable source) {
        // For now, do nothing or log. In a real app, this might trigger UI refresh.
        // System.out.println("User " + name + " notified of change in " + source);
    }

    @Override
    public String toString() {
        return name + " (" + role + ")";
    }
}
