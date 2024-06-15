package model;
import util.Gender;
import util.KCalCalculationStrategies.HarrisBenedictOriginal;
import util.KCalCalculationStrategies.HarrisBenedictRevised;
import util.KCalCalculationStrategies.KCalCalculationStrategy;
import util.KCalCalculationStrategies.MifflinStJeor;
import util.PhysicalActivity;
import java.util.*;

public class Adventure extends BaseClass{
    private final List<CrewMember> crew;
    private final Map<UUID, Meal> meals;
    private final Map<UUID, Double> mealRatios;

    public Adventure(String name) {
        this.name = "Unnamed Adventure";
        crew = new ArrayList<>();
        meals = new HashMap<>();
        mealRatios = new HashMap<>();
    }

    /**
     * Add a new meal to meals hashmap and the ratios hashmap using the same key.
     */
    public void addMeal() {
        double weightedValue = giveSpaceForAnotherEntry();
        Meal newMeal = new Meal();
        UUID key = UUID.randomUUID();
        putEntry(key, weightedValue, newMeal);
    }
    public void addCrewMember(String name, int age, double height, double weight, Gender gender, PhysicalActivity activity) {
        CrewMember newCrewMember = new CrewMember(name, age, height, weight, gender, activity);
        crew.add(newCrewMember);
    }
    public void printMembersDailyKCalNeed() {
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
