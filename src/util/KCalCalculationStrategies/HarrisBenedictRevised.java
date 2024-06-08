package util.KCalCalculationStrategies;

import util.Gender;

public class HarrisBenedictRevised implements KCalCalculationStrategy {
    public double determineBMR(int age, double height, double weight, Gender gender) {
        double BMR;
        if (gender == Gender.MALE) {
            BMR = 13.397 * weight + 4.799 * height - 5.677 * age + 88.362;
        } else
            BMR = 9.247 * weight + 3.098 * height - 4.330 * age + 447.593;
        return BMR;
    }
}
