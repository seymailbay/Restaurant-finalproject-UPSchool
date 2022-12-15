package restaurantSimulation.meal;

import java.util.ArrayList;
import java.util.List;

public class MealType {

    /***
     *
     * Yemek listesi oluşturulur.
     */

    public static List<Meal> getMealList(){

        List<Meal> meal = new ArrayList<>();
        meal.add(new Meal("Pizza",5000,5000));
        meal.add(new Meal("Hamburger",5000,3000));
        meal.add(new Meal("Doner Kebab",2000,1500));
        meal.add(new Meal("Sandwich",3000,4000));
        meal.add(new Meal("Fries",2000,2000));




        return meal;
    }

}
