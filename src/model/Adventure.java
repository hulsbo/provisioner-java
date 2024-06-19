package model;
import util.CrewMember.Gender;
import util.CrewMember.KCalCalculationStrategies.HarrisBenedictOriginal;
import util.CrewMember.KCalCalculationStrategies.HarrisBenedictRevised;
import util.CrewMember.KCalCalculationStrategies.KCalCalculationStrategy;
import util.CrewMember.KCalCalculationStrategies.MifflinStJeor;
import util.CrewMember.PhysicalActivity;
import util.baseclass.ChildWrapper;

import java.util.*;

public class Adventure extends BaseClass{
    private final List<CrewMember> crew = new ArrayList<>();
    private double crewDailyKcalNeed;
    private double weight;
    private int days;
    private final Map<UUID, Double> mealWeights = new LinkedHashMap<>(); // NOTE: This one uses not ChildMap keys.
    private final Map<UUID, Double> ingredientWeights = new LinkedHashMap<>();
    private final KCalCalculationStrategy kCalCalculationStrategy;

    public Adventure(KCalCalculationStrategy calcStrategy) {
        this.kCalCalculationStrategy = calcStrategy;
    }

    @Override
    public void setEnergyDensity() {
        super.setEnergyDensity();
        setWeight();
    }



    public void setWeight() {
        if (energyDensity != 0) {
            this.weight = (crewDailyKcalNeed*days)/energyDensity;
        }
    }

    public double getWeight() {
        return weight;
    }

    public Map<UUID, Double> getMealWeights() {
        return mealWeights;
    }

    private void setMealWeights() { // refer to setIngredientWeights() for complete update.

        mealWeights.clear();

        Set<UUID> keys = childMap.keySet();
        for (UUID key : keys) {
            // NOTE: To be consistent with ingredientWeights, we here use the id of the Meal object itself.
            UUID mealKey = childMap.get(key).getChild().getId();
            mealWeights.put(mealKey, weight*childMap.get(key).getRatio());
        }
    }

    public void setMealAndIngredientWeights() {

        ingredientWeights.clear();

        setMealWeights(); // Ingredient weights depends on an updated mealWeights field.

        Set<UUID> mealKeys = mealWeights.keySet();

        for (UUID mealKey : mealKeys) { // For each meal, calculate its child ingredients weights and save in ingredientWeights.

            Map<UUID, ChildWrapper> mealIngredients = childMap.get(mealKey).getChild().childMap;

            Set<UUID> ingredientKeys = mealIngredients.keySet();

            for (UUID ingredientKey : ingredientKeys) {
                // NOTE: Since there's no direct connection to this object and the ingredient, we use the id of the ingredient itself.
                ingredientWeights.put(ingredientKey, mealWeights.get(mealKey)*mealIngredients.get(ingredientKey).getRatio());
            }
        }
    }

    public void setNutrientsMap() {
       super.setNutrientsMap();
       setCrewDailyKcalNeed();
       setMealAndIngredientWeights();
    }

    /**
     * Add a new meal to meals hashmap and the ratios hashmap using the same key.
     *
     * @return UUID key of newChild
     */
    public UUID putChild(Meal newMeal) {
        double weightedValue = giveSpaceForAnotherEntry();
        return super.putChild(newMeal, weightedValue, 0.0);
    }

    public void addCrewMember(String name, int age, double height, double weight, Gender gender, PhysicalActivity activity, KCalCalculationStrategy kCalCalculationStrategy) {
        CrewMember newCrewMember = new CrewMember(name, age, height, weight, gender, activity, kCalCalculationStrategy);
        crew.add(newCrewMember);
        setCrewDailyKcalNeed();
    }

    public void setCrewDailyKcalNeed() {
        double sum = 0;
        for (CrewMember crewMember : crew) {
            sum += crewMember.getDailyKCalNeed();
        }
        System.out.println(sum);
        this.crewDailyKcalNeed = sum;
        setWeight();
    }

    public void setDays(int days) {
        this.days = days;
    }

    public double getDays() {
        return days;
    }

    public void getInfo() {
        System.out.println();
        System.out.println("Summary " + "of " + getClass().getSimpleName() + " \"" + getName() + "\":");
        System.out.println();
        System.out.println("Crew members: ".toUpperCase());
        int i = 1;
        for (CrewMember crewMember : crew) {
            System.out.println();
            System.out.printf("%25s %s %n", "Crew member " + i + ":", crewMember.getName());
            System.out.printf("%25s %s %n", "Gender:"  ,crewMember.getGender().toString().toLowerCase());
            System.out.printf("%25s %s %n", "Age:", crewMember.getAge());
            System.out.printf("%25s %s %n", "Activity level:", crewMember.getActivity().toString().toLowerCase());
            System.out.printf("%25s %.0f KCal %n", "Daily KCal need:", crewMember.getDailyKCalNeed());

            i++;
        }
        System.out.println();
        System.out.printf("%25s %.0f KCal %n", "Daily KCal need crew:", crewDailyKcalNeed);

        System.out.println();

        System.out.printf("Meals for %s days:".toUpperCase(), days);
        System.out.println();
        System.out.println();
        childMap.forEach((key, value) -> {
            System.out.printf("%10s |", value.getChild().getName());
            System.out.printf(" ratio: " + "%5.1f %%", childMap.get(key).getRatio()*100);
            Set<String> nutrients = childMap.get(key).getChild().getNutrientsMap().keySet();
            for (String nutrient : nutrients) {
                System.out.printf( " | %s: %4.1f %%", nutrient, childMap.get(key).getChild().getNutrientsMap().get(nutrient)*100);
            }
            System.out.printf(" | calc. weight: " + "%4.2f kg", mealWeights.get(childMap.get(key).getChild().getId()));
            System.out.println();
            System.out.println();
            // For adventures, also sum each ingredient for each meal
            Map<UUID, ChildWrapper> childMapIngredient = value.getChild().childMap;
            childMapIngredient.forEach((childMapIngredientKey, childMapIngredientValue) -> {
                System.out.printf("%15s |", childMapIngredientValue.getChild().getName());
                System.out.printf(" ratio: " + "%5.1f %%", childMapIngredient.get(childMapIngredientKey).getRatio()*100);
                Set<String> ingredientNutrients = childMapIngredient.get(childMapIngredientKey).getChild().getNutrientsMap().keySet();
                for (String nutrient : ingredientNutrients) {
                    System.out.printf( " | %s: %4.1f %%", nutrient, childMapIngredient.get(childMapIngredientKey).getChild().getNutrientsMap().get(nutrient)*100);
                }
                System.out.printf(" | calc. weight: " + "%4.2f kg", ingredientWeights.get(childMapIngredient.get(childMapIngredientKey).getChild().getId()));
                System.out.println();
            });
            System.out.println();
        });

        // Summary
        System.out.printf("%10s |", getClass().getSimpleName());

        Set<UUID> children = childMap.keySet();
        double sum = 0;

        for (UUID id : children) {
            sum += childMap.get(id).getRatio();
        }

        System.out.printf(" ratio: " + "%5.1f %%", sum * 100);

        Set<String> nutrients = getNutrientsMap().keySet();
        for (String nutrient : nutrients) {
            System.out.printf( " | %s: %4.1f %%", nutrient, getNutrientsMap().get(nutrient)*100);
        }
        System.out.printf(" | calc. weight: " + "%4.2f kg", getWeight());
        System.out.println();
        System.out.println();
        System.out.printf("Energy Density of " + getClass().getSimpleName() + ": %4.0f KCal/Kg %n", energyDensity);
        System.out.println();
        System.out.println("END OF SUMMARY");
    }

}
