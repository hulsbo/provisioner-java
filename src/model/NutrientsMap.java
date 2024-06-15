package model;

import java.util.*;

public class NutrientsMap extends AbstractMap<String, Double> {
    private final Map<String, Double> nutrientsMap = new HashMap<>();

    public NutrientsMap() {
        nutrientsMap.put("protein", 5.0);
        nutrientsMap.put("fat", 5.0);
        nutrientsMap.put("carbs", 5.0);
        nutrientsMap.put("water", 5.0);
        nutrientsMap.put("fiber", 5.0);
        nutrientsMap.put("salt", 5.0);
    }

    @Override
    public Set<Entry<String, Double>> entrySet() {
        return nutrientsMap.entrySet();
    }

    @Override
    public Double put(String key, Double value) {
        if (nutrientsMap.containsKey(key)) {
            nutrientsMap.put(key, value);
        } else {
            throw new IllegalArgumentException("Invalid key: " + key);
        }
        return value;
    }

    @Override
    public Double remove(Object key) {
        throw new UnsupportedOperationException("Keys cannot be removed in a nutrientsMap.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("A nutrientsMaps cannot be cleared.");
    }

}
