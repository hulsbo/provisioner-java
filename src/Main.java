import model.Adventure;
import model.BaseClass;
import model.Meal;

import java.util.Scanner;

import static util.Gender.FEMALE;
import static util.Gender.MALE;
import static util.PhysicalActivity.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Meal testMeal = new Meal("test");

        // testMeal.addCrewMember("Oskar Huledal", 35, 186, 75, MALE, HEAVY);
        // testMeal.addCrewMember("Lovisa Huledal", 30, 180, 70, FEMALE, MODERATE);
//
        // testMeal.showMembersDailyKCalNeed();

        for (;;) {
            System.out.print("Type the name of the meal: ");
            String name = input.nextLine();
            testMeal.addIngredient(name);
            testMeal.printEntries();
            testMeal.setNutrientsMap();
            System.out.println(testMeal.getNutrientsMap());

            System.out.print("Type the name of the meal: ");
            String name2 = input.nextLine();
            testMeal.addIngredient(name2);
            testMeal.printEntries();


            System.out.print("Type the name of the meal to remove: ");
            String removeString = input.nextLine();
            testMeal.removeEntry(removeString);
            testMeal.printEntries();


        }

    }
}