package model;
import util.Gender;
import util.KCalCalculationStrategies.HarrisBenedictOriginal;
import util.KCalCalculationStrategies.HarrisBenedictRevised;
import util.KCalCalculationStrategies.KCalCalculationStrategy;
import util.KCalCalculationStrategies.MifflinStJeor;
import util.PhysicalActivity;
import java.util.*;

public class Adventure extends BaseClass{
    private final String name;
    private final List<CrewMember> crew;
    private final Map<UUID, Meal> meals;
    private final Map<UUID, Double> mealRatios;

    public Adventure(String name) {
        this.name = name;
        crew = new ArrayList<>();
        meals = new HashMap<>();
        mealRatios = new HashMap<>();
    }

    public void addCrewMember(String name, int age, double height, double weight, Gender gender, PhysicalActivity activity) {
        CrewMember newCrewMember = new CrewMember(name, age, height, weight, gender, activity);
        crew.add(newCrewMember);
    }
    public void printMeals() {
        meals.forEach((key, value) -> {
            System.out.printf("%s:%n", value.getName());
            System.out.printf("%s %% %n", mealRatios.get(key)*100);
        });
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

    /**
     * Add a new meal to meals hashmap and the ratios hashmap using the same key.
     */
    public void addMeal(String defaultName) {
        if (nameIndex.containsValue(nameIndex.get(defaultName))) {
            System.out.println("Entry with that name already exist.");
            return;
        }
        double weightedValue = giveSpaceForAnotherEntry();
        Meal newMeal = new Meal(defaultName);
        UUID key = UUID.randomUUID();
        putEntry(key, weightedValue, newMeal);
    }

    @Override
    public String getName() {
        return this.name;
    }

    // Other methods for managing the crew if needed
}
