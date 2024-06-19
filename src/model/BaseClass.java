package model;

import util.baseclass.ChildWrapper;
import util.baseclass.NutrientsMap;

import java.util.*;

public abstract class BaseClass {
    protected final NutrientsMap nutrientsMap = new NutrientsMap();
    protected final Map<UUID, ChildWrapper> childMap = new LinkedHashMap<>();
    protected final Map<String, UUID> nameIndex = new HashMap<>();
    protected double energyDensity;
    protected String name;
    protected UUID uuid;

    public BaseClass() {
        UUID id = UUID.randomUUID();
        this.uuid = id;
        this.name = "Unnamed " + getClass().getSimpleName();
        Manager.register(id, this);
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;

        // Update name index for all parents
        Set<BaseClass> parents = Manager.findParents(this.getId());
        for (BaseClass parent : parents) {
            parent.updateNameIndex();
        }
    }

    public UUID getId() {
        return this.uuid;
    }

    public void setEnergyDensity() {
        double carbsRatio = nutrientsMap.get("carbs");
        double proteinRatio = nutrientsMap.get("protein");
        double fatRatio = nutrientsMap.get("fat");

        this.energyDensity = (carbsRatio+proteinRatio)*4000+fatRatio*9000;
    }

    private double getEnergyDensity() {
        return this.energyDensity;
    }

    protected NutrientsMap getNutrientsMap() {
        return nutrientsMap;
    }


    /** Recalculates the nutrientsMap() based on childMap and ratiosMap
     * This method should be run if childMap has been updated.
     */
    protected void setNutrientsMap() {
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
        setEnergyDensity();
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

    /**
     * Print info about this object.
     */
    public void getInfo() {
        System.out.println();
        System.out.println("Summary " + "of " + getClass().getSimpleName() + " \"" + getName() + "\":");
        System.out.println();
        childMap.forEach((key, value) -> {
            System.out.printf("%10s |", value.getChild().getName());
            System.out.printf(" ratio: " + "%5.1f %%", childMap.get(key).getRatio()*100);
            if ( getClass() != Adventure.class) {
                System.out.printf(" | weight: " + "%5.1f g", childMap.get(key).getRecipeWeight());
            }
            Set<String> nutrients = childMap.get(key).getChild().getNutrientsMap().keySet();
            for (String nutrient : nutrients) {
                System.out.printf( " | %s: %4.1f %%", nutrient, childMap.get(key).getChild().getNutrientsMap().get(nutrient)*100);
            }
            System.out.println();
        });

        System.out.println();
        System.out.printf("%10s |", getClass().getSimpleName());
        Set<UUID> children = childMap.keySet();
        double sum = 0;

        for (UUID id : children) {
            sum += childMap.get(id).getRatio();
        }

        System.out.printf(" ratio: " + "%5.1f %%", sum * 100);

        if ( getClass() != Adventure.class) {
            sum = 0;
            for (UUID id : children) {
                sum += childMap.get(id).getRecipeWeight();
            }
            System.out.printf(" | weight: " + "%5.1f g", sum);
        }

        Set<String> nutrients = getNutrientsMap().keySet();
        for (String nutrient : nutrients) {
            System.out.printf( " | %s: %4.1f %%", nutrient, getNutrientsMap().get(nutrient)*100);
        }
        System.out.println();
        System.out.println();

        System.out.printf("Energy Density of " + getClass().getSimpleName() + ": %4.0f KCal/Kg %n%n", energyDensity);
    }

    /**
     * Base method for putting new children and updating name index. See subclass for full method.
     * @param newChild The new child to add.
     * @param newWeightedValue The weighted value of the child.
     * @param absWeight The absolute weight of the child.
     * @return UUID key of newChild
     */
    protected UUID putChild(BaseClass newChild, Double newWeightedValue, Double absWeight) {
        ChildWrapper newChildWrapper = new ChildWrapper(newChild, newWeightedValue, absWeight);
        childMap.put(newChild.getId(), newChildWrapper);
        updateNameIndex();
        setNutrientsMap();
        return newChild.getId();
    }

    /**
     * Update the weighted value of an existing child.
     * The key must be present in childMap.
     * @param key Key of the child to update.
     * @param newWeightedValue The new weighted value.
     * @throws IllegalArgumentException if the key is not present in childMap.
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
     * Update the weighted value of an existing child.
     * The key must be present in childMap.
     * @param key Key of the child to update.
     * @param newAbsWeight The new weighted value.
     * @throws IllegalArgumentException if the key is not present in childMap.
     */

    protected void modifyAbsWeight(UUID key, Double newAbsWeight) {
        if (!childMap.containsKey(key)) {
            throw new IllegalArgumentException("Child with key not present in childMap.");
        }
        ChildWrapper childWrapper = childMap.get(key);
        childWrapper.setRecipeWeight(newAbsWeight);
        childMap.put(key, childWrapper);
        setNutrientsMap();
    }

    /**
     * Update the child of an existing ChildWrapper
     * The key must be present in childMap.
     * @param key Key of the child to update.
     * @param newChild The new child object.
     * @throws IllegalArgumentException if the key is not present in childrenMap.
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
     * Removes child using name, updates name index and scales the ratioMap so all children sum remains equal to 1.
     * @param name Name of the child to remove.
     */
    public void removeChild(String name) {
        UUID key = nameIndex.get(name);
        if (key == null) {
            System.out.println("No child with that name.");
            return;
        }
        double weightedValue = childMap.get(key).getRatio();
        childMap.remove(key);
        updateNameIndex();
        scaleEntriesOnRemoval(weightedValue);
        setNutrientsMap();
        Manager.removeObject(key);
    }

    protected void updateNameIndex() {
        nameIndex.clear();
        for (UUID key : childMap.keySet()) {
            nameIndex.put(childMap.get(key).getChild().getName(), key);
        }
    }

}
