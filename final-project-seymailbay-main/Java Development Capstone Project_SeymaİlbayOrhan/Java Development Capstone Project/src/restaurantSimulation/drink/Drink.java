package restaurantSimulation.drink;

import java.util.HashMap;

public class Drink {

    private String drinkName;
    private int preparationTime;
    private int drinkingTime;

    public Drink(String drinkName, int preparationTime, int drinkingTime) {
        this.drinkName = drinkName;
        this.preparationTime = preparationTime;
        this.drinkingTime = drinkingTime;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getDrinkingTime() {
        return drinkingTime;
    }

    public void setDrinkingTime(int drinkingTime) {
        this.drinkingTime = drinkingTime;
    }
    @Override
    public String toString() {
        return drinkName + " " + preparationTime + "ms" ;
    }
}
