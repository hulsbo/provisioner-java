package model;
import util.Gender;
import util.KCalCalculationStrategies.HarrisBenedictOriginal;
import util.KCalCalculationStrategies.HarrisBenedictRevised;
import util.KCalCalculationStrategies.KCalCalculationStrategy;
import util.KCalCalculationStrategies.MifflinStJeor;
import util.PhysicalActivity;

import java.util.ArrayList;
import java.util.List;

public class Adventure {
    private final List<Crewmember> crew;

    public Adventure() {
        crew = new ArrayList<>();
    }

    public void addCrewmember(String name, int age, double height, double weight, Gender gender, PhysicalActivity activity) {
        Crewmember newCrewmember = new Crewmember(name, age, height, weight, gender, activity);
        crew.add(newCrewmember);
    }

    public void showMembersDailyKCalNeed() {

        for (Crewmember crewmember : crew) {
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
