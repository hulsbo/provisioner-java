package model;

import util.baseclass.ChildWrapper;
import util.baseclass.NutrientsMap;

import java.util.*;

public abstract class BaseClass {
    private final NutrientsMap nutrientsMap = new NutrientsMap();
    protected final Map<UUID, ChildWrapper> childMap = new HashMap<>();
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
        this.name = name;
    }

    public void setChildName(UUID id, String name) {
        if (nameIndex.containsValue(nameIndex.get(name))) {
            System.out.println("Child with that name already exist.");
            return;
        }
        if (!childMap.containsKey(id)) {
            System.out.println("Child with that key is not present in childMap.");
            return;
        }
        childMap.get(id).getChild().setName(name);
        updateNameIndex();
    }

    public UUID getId() {
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
        for (UUID key : childMap.keySet()) {
            double ratio = childMap.get(key).getRatio();
            BaseClass baseClass = childMap.get(key).getChild();
            NutrientsMap baseClassNutrients = baseClass.getNutrientsMap();

            for (String nutrient : nutrients) {
                nutrientsMap.merge(nutrient, baseClassNutrients.get(nutrient),
                        (oldValue, newValue) -> (oldValue + newValue*ratio));
            }
        }
    }


    /**
     * Resizes the percentage hashmap values assuming all take equal space, so their sum is one.
     * If empty returned weight is 1.
     * @return the value of the new allocated space
     */
    protected double giveSpaceForAnotherEntry() {
        double size = childMap.size();
        double oldEntriesAllowedSpace = size / (size + 1);
        double sumNewValue = 0.0;

        if (!childMap.isEmpty()) {
            for (UUID key : childMap.keySet()) {
                double oldValue = childMap.get(key).getRatio();
                double newValue = oldValue * oldEntriesAllowedSpace;
                sumNewValue += newValue;
                childMap.get(key).setRatio(newValue);
            }
        }
        return 1 - sumNewValue;
    }

    /**
     * For a given weightedValue space, enlarge the other weighted values in proportion for sum to remain 1.
     */
    protected void scaleEntriesOnRemoval(double weightedValue) {
        double scaleFactor = 1/(1-weightedValue);
        for (UUID key : childMap.keySet()) {
            double value = childMap.get(key).getRatio();
            childMap.get(key).setRatio(value*scaleFactor);
        }
    }
    public void printEntries() {
        childMap.forEach((key, value) -> {
            System.out.printf("%s - ", value.getChild().getName());
            System.out.printf("%s %% %n", childMap.get(key).getRatio()*100);
        });
    }

    /**
     * Base method for putting new entries and updating name index. See subclass for full method.
     * @param newChild The new child to add.
     * @param newWeightedValue The weighted value of the child.
     * @param absWeight The absolute weight of the child.
     * @return UUID key of newChild
     */
    public UUID putChild(BaseClass newChild, Double newWeightedValue, Double absWeight) {
        ChildWrapper newChildWrapper = new ChildWrapper(newChild, newWeightedValue, absWeight);
        childMap.put(newChild.getId(), newChildWrapper);
        updateNameIndex();
        setNutrientsMap();
        return newChild.getId();
    }

    /**
     * Update the weighted value of an existing entry.
     * The key must be present in entriesMap.
     * @param key Key of the entry to update.
     * @param newWeightedValue The new weighted value.
     * @throws IllegalArgumentException if the key is not present in entriesMap.
     */
    protected void modifyRatio(UUID key, Double newWeightedValue) {
        if (!childMap.containsKey(key)) {
            throw new IllegalArgumentException("Child with key not present in childMap.");
        }
        ChildWrapper childWrapper = childMap.get(key);
        childWrapper.setRatio(newWeightedValue);
        childMap.put(key, childWrapper);
        setNutrientsMap();
    }

    /**
     * Update the weighted value of an existing entry.
     * The key must be present in entriesMap.
     * @param key Key of the entry to update.
     * @param newAbsWeight The new weighted value.
     * @throws IllegalArgumentException if the key is not present in entriesMap.
     */

    protected void modifyAbsWeight(UUID key, Double newAbsWeight) {
        if (!childMap.containsKey(key)) {
            throw new IllegalArgumentException("Child with key not present in childMap.");
        }
        ChildWrapper childWrapper = childMap.get(key);
        childWrapper.setAbsWeight(newAbsWeight);
        childMap.put(key, childWrapper);
        setNutrientsMap();
    }

    /**
     * Update the entry object of an existing entry.
     * The key must be present in entriesMap.
     * @param key Key of the entry to update.
     * @param newChild The new entry object.
     * @throws IllegalArgumentException if the key is not present in entriesMap.
     */
    protected void modifyChild(UUID key, BaseClass newChild) {
        if (!childMap.containsKey(key)) {
            throw new IllegalArgumentException("Child with key not present in childMap.");
        }
        ChildWrapper childWrapper = childMap.get(key);
        childWrapper.setChild(newChild);
        childMap.put(key, childWrapper);
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
        double weightedValue = childMap.get(key).getRatio();
        childMap.remove(key);
        updateNameIndex();
        scaleEntriesOnRemoval(weightedValue);
    }

    protected void updateNameIndex() {
        nameIndex.clear();
        for (UUID key : childMap.keySet()) {
            nameIndex.put(childMap.get(key).getChild().getName(), key);
        }
    }

}
