package model;

import java.util.*;

public class Meal extends BaseClass {
    private final String name;

    public Meal(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void addIngredient(String defaultName) {
        if (nameIndex.containsValue(nameIndex.get(defaultName))) {
            System.out.println("Entry with that name already exist.");
            return;
        }
        double weightedValue = giveSpaceForAnotherEntry();
        Ingredient newIngredient = new Ingredient(defaultName);
        UUID key = UUID.randomUUID();
        putEntry(key, weightedValue, newIngredient);
    }
}
