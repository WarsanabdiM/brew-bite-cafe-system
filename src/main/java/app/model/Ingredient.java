package app.model;

import java.util.Objects;

public class Ingredient {

    private final String id;
    private String name;
    private String unit; // e.g., "shots", "ml", "grams"

    public Ingredient(String id, String name, String unit) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.unit = Objects.requireNonNull(unit);
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = Objects.requireNonNull(unit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;
        Ingredient that = (Ingredient) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return name + " (" + unit + ")";
    }
}
