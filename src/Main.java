import model.Adventure;
import model.Meal;

import java.util.Scanner;
import java.util.UUID;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Adventure testAdventure = new Adventure();

        // testMeal.addCrewMember("Oskar Huledal", 35, 186, 75, MALE, HEAVY);
        // testMeal.addCrewMember("Lovisa Huledal", 30, 180, 70, FEMALE, MODERATE);
//
        // testMeal.showMembersDailyKCalNeed();


        for (;;) {
            System.out.println("Enter name of Adventure:");
            String name = input.nextLine();
            UUID key = testAdventure.putChild(new Meal());
            testAdventure.setName(name);
            System.out.println("Enter name of Meal:");
            String mealName = input.nextLine();
            testAdventure.setChildName(key, mealName);
            testAdventure.printChildren();
            testAdventure.removeChild(mealName);
        }


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