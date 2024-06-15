package model;

import java.util.*;

public class Meal extends BaseClass {
    private final Map<UUID, Double> absWeightMap = new HashMap<>();

    public void addIngredient(Ingredient newIngredient) {
        UUID id = newIngredient.getId();
        putEntry(id, 0.0, newIngredient);
        absWeightMap.put(id, 0.0);
    }

    /**
     * @param id id of ingredient
     * @param weight absolute weight in grams of ingredient
     */
    public void modifyWeightOfIngredient(UUID id, double weight) {
        if (weight == 0) {
            throw new IllegalArgumentException("The weight cannot be 0.");
        }

        absWeightMap.put(id, weight);

        // Calculate the current total weight
        double totalWeight = 0.0;

        Set<UUID> entries = absWeightMap.keySet();

        for (UUID entry : entries) {
            totalWeight += absWeightMap.get(entry);
        }

        // update all ratios
        for (UUID entry : entries) {
            double weightedValue = absWeightMap.get(entry) / totalWeight;
            modifyEntry(entry, weightedValue);
        }

    }
}
