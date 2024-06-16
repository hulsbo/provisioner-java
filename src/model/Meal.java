package model;

import java.util.*;

public class Meal extends BaseClass {

public UUID putChild(Ingredient newIngredient) {
    return super.putChild(newIngredient, 0.0, 0.0);
}

    /**
     * @param id id of ingredient
     * @param weight absolute weight in grams of ingredient
     */
    public void modifyWeightOfIngredient(UUID id, double weight) {
        if (childMap.get(id) == null) {
            throw new IllegalArgumentException("No Ingredient with such name exist.");
        }
        if (weight == 0) {
            throw new IllegalArgumentException("The weight cannot be 0.");
        }

        childMap.get(id).setAbsWeight(weight);

        // Calculate the current total weight
        double totalWeight = 0.0;

        Set<UUID> keys = childMap.keySet();

        for (UUID key : keys) {
            totalWeight += childMap.get(key).getAbsWeight();
        }

        // update all ratios
        for (UUID key : keys) {
            double weightedValue = childMap.get(key).getAbsWeight() / totalWeight;
            modifyRatio(key, weightedValue);
        }

    }
}
