package model;

import java.util.*;

public class Meal extends BaseClass {

    public void addIngredient(Ingredient newIngredient) {
        putEntry(newIngredient.getId(), 0.0, newIngredient);
    }
    }
}
