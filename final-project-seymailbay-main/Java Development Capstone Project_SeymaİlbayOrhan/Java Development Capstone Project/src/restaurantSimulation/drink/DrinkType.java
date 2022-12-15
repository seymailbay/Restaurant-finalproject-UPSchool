package restaurantSimulation.drink;

import java.util.ArrayList;
import java.util.List;

public class DrinkType {

    /***
     *
     * İçecek listesi oluşturulur
     */

    public static List<Drink> getDrinkList(){
        List<Drink> drink = new ArrayList<>();
        drink.add(new Drink("Water",500,1000));
        drink.add(new Drink("Juice",1000,1000));
        drink.add(new Drink("Lemonade",1000,1000));
        drink.add(new Drink("Tea",500,1000));

        return drink;
    }

}
