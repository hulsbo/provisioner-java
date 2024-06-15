package model;

import java.util.*;

public abstract class BaseClass {
    private final NutrientsMap nutrientsMap = new NutrientsMap();
    protected final Map<UUID, BaseClass> entriesMap = new HashMap<>();
    protected final Map<UUID, Double> ratioMap = new HashMap<>();
    protected final Map<String, UUID> nameIndex = new HashMap<>();
    protected String name;
    protected UUID uuid;

    public BaseClass() {
        this.uuid = UUID.randomUUID();
        this.name = "Unnamed " + getClass().getSimpleName();
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        if (nameIndex.containsValue(nameIndex.get(name))) {
            System.out.println(getClass().getSimpleName() + " with that name already exist.");
            return;
        }
        this.name = name;
    }

    protected UUID getId() {
        return this.uuid;
    }

    public NutrientsMap getNutrientsMap() {
        return nutrientsMap;
    }


    /** Recalculates the nutrientsMap() based on entriesMap and ratiosMap
     * This method should be run if any of these maps have been mutated.
     */
    public void setNutrientsMap() {
        Set<String> nutrients = nutrientsMap.keySet();

        // Reset this baseclass nutrientsMap
        for (String nutrient : nutrients) {
            nutrientsMap.put(nutrient, 0.0);
        }

        // Add all weighted nutrients of the baseclass to this baseclass' nutrientMap
        for (UUID key : entriesMap.keySet()) {
            double ratio = ratioMap.get(key);
            BaseClass baseClass = entriesMap.get(key);
            NutrientsMap baseClassNutrients = baseClass.getNutrientsMap();

            for (String nutrient : nutrients) {
                nutrientsMap.merge(nutrient, baseClassNutrients.get(nutrient),
                        (oldValue, newValue) -> (oldValue + newValue*ratio));
            }
        }
    }


    /**
     * Resizes the percentage hashmap values assuming all take equal space and they take up all space.
     * If empty returned weight is 1.
     * @return the value of the new allocated space
     */
    protected double giveSpaceForAnotherEntry() {
        double size = ratioMap.size();
        double oldEntriesAllowedSpace = size / (size + 1);
        double sumNewValue = 0.0;

        if (!ratioMap.isEmpty()) {
            for (UUID key : ratioMap.keySet()) {
                double oldValue = ratioMap.get(key);
                double newValue = oldValue * oldEntriesAllowedSpace;
                sumNewValue += newValue;
                ratioMap.replace(key, newValue);
            }
        }
        return 1 - sumNewValue;
    }

    /**
     * For a given weightedValue space, enlarge the other weighted values in proportion for sum to remain 1.
     */
    protected void scaleEntriesOnRemoval(double weightedValue) {
        double scaleFactor = 1/(1-weightedValue);
        for (UUID key : ratioMap.keySet()) {
            double value = ratioMap.get(key);
            ratioMap.put(key, value*scaleFactor);
        }
    }
    public void printEntries() {
        entriesMap.forEach((key, value) -> {
            System.out.printf("%s - ", value.getName());
            System.out.printf("%s %% %n", ratioMap.get(key)*100);
        });
    }

    /**
     * Base method for putting new entries and updating name index. See subclass for full method.
     * @param key Key of the entry to add.
     * @param newWeightedValue The weighted value of the new entry.
     * @param newEntry The new entry object of class BaseClass.
     */
    protected void putEntry(UUID key, Double newWeightedValue, BaseClass newEntry) {
        ratioMap.put(key, newWeightedValue);
        entriesMap.put(key, newEntry);
        updateNameIndex();
        setNutrientsMap();
    }

    /**
     * Update the weighted value of an existing entry.
     * The key must be present in entriesMap.
     * @param key Key of the entry to update.
     * @param newWeightedValue The new weighted value.
     * @throws IllegalArgumentException if the key is not present in entriesMap.
     */
    protected void putEntry(UUID key, Double newWeightedValue) {
        if (!ratioMap.containsKey(key)) {
            throw new IllegalArgumentException("Key not present in ratioMap.");
        }
        ratioMap.put(key, newWeightedValue);
        setNutrientsMap();
    }

    /**
     * Update the entry object of an existing entry.
     * The key must be present in entriesMap.
     * @param key Key of the entry to update.
     * @param newEntry The new entry object.
     * @throws IllegalArgumentException if the key is not present in entriesMap.
     */
    protected void putEntry(UUID key, BaseClass newEntry) {
        if (!entriesMap.containsKey(key)) {
            throw new IllegalArgumentException("Key not present in entriesMap.");
        }
        entriesMap.put(key, newEntry);
        setNutrientsMap();
    }


    /**
     * Removes entry using name, updates name index and scales the ratioMap so all entries sum remains equal to 1.
     * @param name Name of the entry to remove.
     */
    public void removeEntry(String name) {
        UUID key = nameIndex.get(name);
        if (key == null) {
            System.out.println("No entry with that name.");
            return;
        }
        double weightedValue = ratioMap.get(key);
        ratioMap.remove(key);
        entriesMap.remove(key);
        updateNameIndex();
        scaleEntriesOnRemoval(weightedValue);
    }

    private void updateNameIndex() {
        nameIndex.clear();
        for (UUID key : entriesMap.keySet()) {
            nameIndex.put(entriesMap.get(key).getName(), key);
        }
    }

}
