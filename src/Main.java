import model.Adventure;
import static util.Gender.FEMALE;
import static util.Gender.MALE;
import static util.PhysicalActivity.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        Adventure test_adventure = new Adventure();

        test_adventure.addCrewmember("Oskar Huledal", 35, 186, 75, MALE, HEAVY);
        test_adventure.addCrewmember("Lovisa Huledal", 30, 180, 70, FEMALE, MODERATE);

        test_adventure.showMembersDailyKCalNeed();



    }
}