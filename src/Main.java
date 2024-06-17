import model.Adventure;
import model.Ingredient;
import model.Manager;
import model.Meal;

import java.security.SecureRandom;
import java.util.*;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner input = new Scanner(System.in);
        Adventure testAdventure = new Adventure();
        Meal testMeal = new Meal();

        // testMeal.addCrewMember("Oskar Huledal", 35, 186, 75, MALE, HEAVY);
        // testMeal.addCrewMember("Lovisa Huledal", 30, 180, 70, FEMALE, MODERATE);
//
        // testMeal.showMembersDailyKCalNeed();
        SecureRandom random = new SecureRandom();
        Set<UUID> keys = new LinkedHashSet<>();


        System.out.println("Input name of first meal:");
        String mealName = input.nextLine();
         // Testing meal
        while (!Objects.equals(mealName, "quit")) {
            Meal newMeal = new Meal();
            newMeal.setName(mealName);
            UUID id = newMeal.getId();
            keys.add(id);
            System.out.println("Input first ingredient of meal:");
            String name = input.nextLine();
            while (!Objects.equals(name, "quit")) {
                UUID key = newMeal.putChild(new Ingredient());
                Manager.getObject(key).setName(name);
                newMeal.modifyWeightOfIngredient(key, random.nextInt(1, 101));
                System.out.println();
                newMeal.getInfo();
                System.out.println("Input next ingredient name: (when done exit with 'quit')");
                name = input.nextLine();
            }
            System.out.println("Input name of next meal: (when done exit with 'quit')");
            mealName = input.nextLine();
        }
            Adventure newAdventure = new Adventure();
            System.out.println("Input name of Adventure: ");
            String adventureName = input.nextLine();
            newAdventure.setName(adventureName);
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