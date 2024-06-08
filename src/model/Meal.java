package model;

import java.util.ArrayList;
import java.util.List;

public class Meal {
    private String mealName;
    private final List<Ingredient> ingredientList;

    public Meal(String name) {
        this.mealName = name;
        this.ingredientList = new ArrayList<>();
    }

    public void addIngredient() {
        Ingredient newIngredient = new Ingredient("Unnamed ingredient");
        ingredientList.add(newIngredient);
    }

    public String getMealName() {
        return mealName;
    }
}
