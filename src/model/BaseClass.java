package model;

import java.util.*;

public abstract class BaseClass {
    private final NutrientsMap nutrientsMap = new NutrientsMap();
    protected final Map<UUID, BaseClass> entriesMap = new HashMap<>();
    protected final Map<UUID, Double> ratioMap = new HashMap<>();
    protected final Map<String, UUID> nameIndex = new HashMap<>();

    public abstract String getName();
    public NutrientsMap getNutrientsMap() {
        return nutrientsMap;
    }
    public void setNutrientsMap() {
        Set<String> nutrients = nutrientsMap.keySet();

        // Reset this baseclass nutrientsMap
        for (String nutrient : nutrients) {
            nutrientsMap.put(nutrient, 0.0);
        }

        // Add all weighted nutrients of the baseclass to this baseclass' nutrientMap
        for (UUID key : entriesMap.keySet()) {
            double propWeight = ratioMap.get(key);
            BaseClass baseClass = entriesMap.get(key);
            NutrientsMap baseClassNutrients = baseClass.getNutrientsMap();

            for (String nutrient : nutrients) {
                nutrientsMap.merge(nutrient, baseClassNutrients.get(nutrient),
                        (oldValue, newValue) -> (oldValue + newValue*propWeight));
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
