import model.Adventure;
import model.Ingredient;
import model.Manager;
import model.Meal;
import util.CrewMember.KCalCalculationStrategies.HarrisBenedictOriginal;
import util.CrewMember.KCalCalculationStrategies.MifflinStJeor;

import java.security.SecureRandom;
import java.util.*;

import static util.CrewMember.Gender.FEMALE;
import static util.CrewMember.Gender.MALE;
import static util.CrewMember.PhysicalActivity.HEAVY;
import static util.CrewMember.PhysicalActivity.MODERATE;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner input = new Scanner(System.in);
        Adventure testAdventure = new Adventure(new HarrisBenedictOriginal());
        Meal testMeal = new Meal();


//
        // testMeal.showMembersDailyKCalNeed();
        SecureRandom random = new SecureRandom();
        Set<UUID> keys = new LinkedHashSet<>();

         // Testing meal
        for (int i = 0 ; i < 2 ; i++) {
            Meal newMeal = new Meal();
            newMeal.setName("Salad " + (i+1));
            UUID id = newMeal.getId();
            keys.add(id);
            for (int j = 0 ; j < 2 ; j++) {
                UUID key = newMeal.putChild(new Ingredient());
                Manager.getObject(key).setName("Green " + (j+1));
                newMeal.modifyWeightOfIngredient(key, random.nextInt(1, 101)); // Gen. random value of ingredient nutrients
                System.out.println();
                newMeal.getInfo();
            }
        }
            Adventure newAdventure = new Adventure(new HarrisBenedictOriginal());
            newAdventure.setName("Salad days");
            newAdventure.setDays(7);
            newAdventure.addCrewMember("Oskar Huledal", 29, 186, 75, MALE, HEAVY, new HarrisBenedictOriginal());
            newAdventure.addCrewMember("Lovisa Huledal", 31, 180, 70, FEMALE, MODERATE, new MifflinStJeor());
            for (UUID key : keys) {
                newAdventure.putChild((Meal) Manager.getObject(key));
            }
            newAdventure.getInfo();



//            Manager.getObject(key).printChildren();
//            System.out.println("Give a weight for " + name + ":");
//            double weight = input.nextDouble();
//            testMeal.modifyWeightOfIngredient(key, weight);
//            testMeal.printChildren();
//
//            input.nextLine();
        }

        // Testing adventure
//        for (;;) {
//            System.out.println("Enter name of Adventure:");
//            String name = input.nextLine();
//            UUID key = testAdventure.putChild(new Meal());
//            testAdventure.setName(name);
//            System.out.println("Enter name of Meal:");
//            String mealName = input.nextLine();
//            testAdventure.setChildName(key, mealName);
//            testAdventure.printChildren();
//        }


        // Testing the absolute value map for meals' ingredients
//        for (;;) {
//            testMeal.putChild(new Ingredient());
//            System.out.print("Type the name of the meal: ");
//            String name = input.nextLine();
//            testMeal.printEntries();
//            testMeal.setNutrientsMap();
//            System.out.println(testMeal.getNutrientsMap());
//
//            System.out.print("Type the name of the meal: ");
//            String name2 = input.nextLine();
//            testMeal.addIngredient(new Ingredient());
//            testMeal.printEntries();
//
//            System.out.print("Type the name of the meal to remove: ");
//            String removeString = input.nextLine();
//            testMeal.removeEntry(removeString);
//            testMeal.printEntries();
//        }

    }