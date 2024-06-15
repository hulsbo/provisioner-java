package model;

import java.util.*;

public class Meal extends BaseClass {
    public Meal() {
        this.name = "Unnamed meal";
    }
    public void addIngredient() {
        double weightedValue = giveSpaceForAnotherEntry();
        Ingredient newIngredient = new Ingredient();
        UUID key = UUID.randomUUID();
        putEntry(key, weightedValue, newIngredient);
    }
}
