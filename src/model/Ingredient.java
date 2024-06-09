package model;

public class Ingredient extends BaseClass {
    private final String name;
    public Ingredient(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
