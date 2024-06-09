package model;
import util.Gender;
import util.KCalCalculationStrategies.HarrisBenedictOriginal;
import util.KCalCalculationStrategies.HarrisBenedictRevised;
import util.KCalCalculationStrategies.KCalCalculationStrategy;
import util.KCalCalculationStrategies.MifflinStJeor;
import util.PhysicalActivity;

import java.util.*;

public class Adventure {
    private final List<CrewMember> crew;
    private final Map<UUID, Meal> meals;
    private final Map<UUID, Double> mealRatios;

    public Adventure() {
        crew = new ArrayList<>();
        meals = new HashMap<>();
        mealRatios = new HashMap<>();
    }

    public void addCrewMember(String name, int age, double height, double weight, Gender gender, PhysicalActivity activity) {
        CrewMember newCrewMember = new CrewMember(name, age, height, weight, gender, activity);
        crew.add(newCrewMember);
    }

    /**
     * Add a new meal to meals hashmap and the ratios hashmap using the same key.
     */
    public void addMeal() {
        UUID key = UUID.randomUUID();
        double weightedValue = giveSpaceForAnotherEntry(mealRatios);

        Meal newMeal = new Meal("Unnamed meal");

        mealRatios.put(key, weightedValue);
        meals.put(key, newMeal);
    }

    public void printMeals() {
        meals.forEach((key, value) -> {
            System.out.printf("%s:%n", value.getMealName());
            System.out.printf("%s %% %n", mealRatios.get(key)*100);
        });
    }

    /**
     * Resizes the percentage hashmap values assuming all take equal space and they take up all space.
     * If empty returned weight is 1.
     * @return the value of the new allocated space
     */
    private double giveSpaceForAnotherEntry(Map<UUID, Double> map) {

        double size = map.size();
        double oldEntriesAllowedSpace = size / (size + 1);
        double sumNewValue = 0.0;

        if (!map.isEmpty()) {
            for (UUID key : map.keySet()) {
                double oldValue = map.get(key);
                double newValue = oldValue * oldEntriesAllowedSpace;
                sumNewValue += newValue;
                map.replace(key, newValue);
            }
        }
        return 1 - sumNewValue;
    }

    public void showMembersDailyKCalNeed() {

        for (CrewMember crewmember : crew) {
            System.out.println();
            System.out.println(crewmember.getName());
            KCalCalculationStrategy harris = new HarrisBenedictOriginal();
            System.out.println("Harris original: " + (int) crewmember.getDailyKCalNeed(harris) + " KCal / Day");
            KCalCalculationStrategy harrisRevised = new HarrisBenedictRevised();
            System.out.println("Harris revised: " + (int) crewmember.getDailyKCalNeed(harrisRevised)  + " KCal / Day");
            KCalCalculationStrategy MifflinStJeor = new MifflinStJeor();
            System.out.println("Mifflin St Jeor: " + (int) crewmember.getDailyKCalNeed(MifflinStJeor)  + " KCal / Day");
        }

    }

    // Other methods for managing the crew if needed
}
