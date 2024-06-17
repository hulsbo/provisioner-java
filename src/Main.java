import model.Adventure;
import model.Ingredient;
import model.Manager;
import model.Meal;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;


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

         // Testing meal
        for (;;) {

            System.out.println("Input name:");
            String name = input.nextLine();
            while (!Objects.equals(name, "quit")) {
                UUID key = testMeal.putChild(new Ingredient());
                Manager.getObject(key).setName(name);
                testMeal.modifyWeightOfIngredient(key, random.nextInt(1, 101));
                System.out.println();
                testMeal.printChildren();
                System.out.println(testMeal.getNutrientsMap());
                System.out.println("Input next ingredient name: (when done exit with 'quit')");
                name = input.nextLine();
            }
            System.out.println(testMeal.getNutrientsMap());


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
}