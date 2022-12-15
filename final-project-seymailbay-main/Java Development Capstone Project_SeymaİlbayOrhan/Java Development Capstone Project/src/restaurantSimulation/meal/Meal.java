package restaurantSimulation.meal;

import java.util.HashMap;
import java.util.List;

public class Meal {

    private String mealName;
    private int preparationTime;
    private int eatingTime;

    public Meal(String mealName, int preparationTime, int eatingTime) {
        this.mealName = mealName;
        this.preparationTime = preparationTime;
        this.eatingTime = eatingTime;
    }
    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getEatingTime() {
        return eatingTime;
    }

    public void setEatingTime(int eatingTime) {
        this.eatingTime = eatingTime;
    }

    @Override
    public String toString() {
        return mealName + " " +
                preparationTime + "ms" ;
    }
}
