package restaurantSimulation;

import restaurantSimulation.drink.Drink;
import restaurantSimulation.meal.Meal;

public class Order {


    Meal meal1;
    Meal meal2;
    Drink drink ;
    int totalPreparationTime;
    int orderIdByCustomer;

    public Order(Meal meal1,Meal meal2, Drink drink,int orderIdByCustomer) {
        this.meal1 = meal1;
        this.drink = drink;
        this.meal2=meal2;
        this.totalPreparationTime =meal1.getPreparationTime()+meal2.getPreparationTime()+drink.getPreparationTime();
        this.orderIdByCustomer=orderIdByCustomer;
    }

    public Order(Meal meal1, Drink drink,int orderIdByCustomer) {
        this.meal1 = meal1;
        this.drink = drink;
        this.totalPreparationTime =meal1.getPreparationTime()+drink.getPreparationTime();
        this.orderIdByCustomer=orderIdByCustomer;
    }

    public Order(Meal meal1,int orderIdByCustomer) {
        this.meal1 = meal1;
        this.totalPreparationTime =meal1.getPreparationTime();
        this.orderIdByCustomer=orderIdByCustomer;
    }

    public Order(Drink drink,int orderIdByCustomer) {
        this.drink = drink;
        this.totalPreparationTime =drink.getPreparationTime();
        this.orderIdByCustomer=orderIdByCustomer;
    }

    /**
     *
     * Seçilen orderlerin constructor tarafından en fazla 2 yemek 1 içecek olacak şekilde kontrolü sağlanır.
     */
    @Override
    public String toString() {
        if(meal2 == null){
            if (drink==null){
                return  "[ " +
                        "Meal 1= " + meal1 +
                        "]";
            }
            else{
                return "[ " +
                        "Meal 1= " + meal1 +
                        ", Drink= " + drink + "]";
            }
        }

        else {
            return "[ " +
                    "Meal 1= " + meal1 +
                    ", Meal 2= " + meal2 +
                    ", Drink= " + drink + "]";
        }
    }

}
