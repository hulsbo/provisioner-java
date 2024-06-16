package model;
import util.CrewMember.Gender;
import util.CrewMember.KCalCalculationStrategies.HarrisBenedictOriginal;
import util.CrewMember.KCalCalculationStrategies.HarrisBenedictRevised;
import util.CrewMember.KCalCalculationStrategies.KCalCalculationStrategy;
import util.CrewMember.KCalCalculationStrategies.MifflinStJeor;
import util.CrewMember.PhysicalActivity;
import java.util.*;

public class Adventure extends BaseClass{
    private final List<CrewMember> crew;

    public Adventure() {
        crew = new ArrayList<>();
    }

    /**
     * Add a new meal to meals hashmap and the ratios hashmap using the same key.
     */
    public void putChild(Meal newMeal) {
        double weightedValue = giveSpaceForAnotherEntry();
        super.putChild(newMeal, weightedValue, 0.0);
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
