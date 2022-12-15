package restaurantSimulation;

import restaurantSimulation.drink.Drink;
import restaurantSimulation.drink.DrinkType;
import restaurantSimulation.meal.Meal;
import restaurantSimulation.meal.MealType;
import restaurantSimulation.myLock.MyLock;

import java.util.List;
import java.util.Random;


public class Customer extends MyLock implements Runnable {
    int ID;
    String name;
    Restaurant restaurant;

    boolean controllerOfEnteredCustomer = true;
    int waitingChecker = 0;
    boolean isCustomerLeft = false;
    boolean isOrderFalse = true;

    public Customer(int ID, String name, Restaurant restaurant) {
        this.ID = ID;
        this.name = name;
        this.restaurant = restaurant;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Uygun masa sayısına göre müşterilerin girişi kontrol edilir.
     * "lock" kullanılarak başka threadin girmesi engellenir.
     * Müşteriler girdikçe masa sayısında azalış olur.
     * Restorana giremeyen müşterilerin beklemesi ve çıkış yapması rastgele belirlenir.
     */

    public void available() {
        lock.lock();

        if (restaurant.getTable() > 0) {
            restaurant.setTable(-1);
            controllerOfEnteredCustomer = true;
        } else {
            if (waitingChecker == 0) {
                Random random = new Random();
                int decision = random.nextInt(2);
                if (decision == 1) {
                    waiting();
                } else {
                    exit();
                }
            }
        }

        lock.unlock();
    }

    public void waiting() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Restaurant FULL!! However, " + getName() + getID() + " decided to wait...");
        controllerOfEnteredCustomer = false;// customer giriş yapana kadar false döner runda while döngüsüne kapılıp sürekli sorulur
        waitingChecker = 1;
        available();
    }

    public synchronized void exit() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Restaurant FULL!! " + getName() + getID() + " decided to exit...");
        isCustomerLeft = true;
        controllerOfEnteredCustomer = false;// giriş yapamaz ve is customer left ile run içerisindeki whiledan çıkar thread sonlanır
    }

    public void entered() {
        System.out.println(getName() + getID() + " entered ");
    }

    /**
     * Müşterilerin siparişleri yemek ve içecek listesinden rastgele belirlenir.
     * Belirlenen siparişler hazırlanan yemeklere eklenir(preperingOrderQueue)
     */
    public void ordered() throws InterruptedException {
        lock.lock();
        Thread.sleep(500);
        try {
            Random random = new Random();
            int decision = random.nextInt(1, 4);

            switch (decision) {
                case 1:
                    List<Meal> mealList = MealType.getMealList(); // create mealList list
                    int meal1Decision = random.nextInt(mealList.size());
                    Meal meal1 = mealList.get(meal1Decision);

                    int meal2Decision = random.nextInt(mealList.size());
                    Meal meal2 = mealList.get(meal2Decision);

                    List<Drink> drinkList = DrinkType.getDrinkList();
                    int drink1Decision = random.nextInt(drinkList.size());
                    Drink drink1 = drinkList.get(drink1Decision);
                    Order order = new Order(meal1, meal2, drink1, getID());
                    int totalTime = order.totalPreparationTime;
                    System.out.println(getName() + getID() + " ordered " + order + "  Total Time: " + totalTime + "ms");
                    restaurant.preparingOrderQueue.add(order);
                    break;
                case 2:
                    mealList = MealType.getMealList();
                    meal1Decision = random.nextInt(mealList.size());
                    meal1 = mealList.get(meal1Decision);
                    drinkList = DrinkType.getDrinkList();
                    drink1Decision = random.nextInt(drinkList.size());
                    drink1 = drinkList.get(drink1Decision);
                    order = new Order(meal1, drink1, getID());
                    totalTime = order.totalPreparationTime;
                    System.out.println(getName() + getID() + " ordered " + order + "  Total Time: " + totalTime + "ms");
                    restaurant.preparingOrderQueue.add(order);
                    break;
                case 3:
                    mealList = MealType.getMealList();
                    meal1Decision = random.nextInt(mealList.size());
                    meal1 = mealList.get(meal1Decision);
                    order = new Order(meal1, getID());
                    totalTime = order.totalPreparationTime;
                    System.out.println(getName() + getID() + " ordered " + order + "  Total Time: " + totalTime + "ms");
                    restaurant.preparingOrderQueue.add(order);
                    break;
            }
        } finally {
            lock.unlock();
        }
    }

    public void customerWaitingForOrder() {
        System.out.println(getName() + getID() + " is waiting for order ");
    }

    /**
     * Hazırlanan sipariş listesi boş olduğu sürece o anki thread uyutulur ve tekrar eatMeal metoduna yönlendirilir.
     * eatmeal metoduna gelen threadlerin daha düzgün kontrolü için sleep uygulanır.
     * Eğer order ve customer ID eşit ise
     */

    public synchronized void eatMeal() {
        try {
            Random random = new Random();
            int eatSecond = random.nextInt(0, 2000);
            Thread.sleep(eatSecond);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            while (restaurant.deliveredOrderQueue.size() <= 0) {
                Random random = new Random();
                int eatsecond2 = random.nextInt(0, 2000);
                Thread.sleep(eatsecond2);
                this.eatMeal();  // o anki threadi referans eder
            }
        } catch (InterruptedException e) {
            eatMeal();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        while (isOrderFalse) {// döngü her loopunda orderId customer Id ile eşleşene kadar delivered queuedan order çekilip if bloğuna sokulur eğer if false dönerse çıkan eleman queueya geri eklenir
            try {
                Thread.sleep(2000);
                Order order1 = restaurant.deliveredOrderQueue.poll();// ilk olarak delivered queue dan ilk eleman alınır order1 olarak değişkene atanır
                if (order1.orderIdByCustomer == getID()) {// Customer Id ile order Id eşit ise customer yer.
                    System.out.println(getName() + getID() + " is eating meal " + order1);
                    isOrderFalse = false;// döngüden çıkmak için koşul false yapılır
                    leaving();
                } else {
                    restaurant.deliveredOrderQueue.add(order1);// eğer if bloğu true dönmezse çıkartılan eleman queuenun en sonuna eklenir.
                }
            } catch (NullPointerException e) {
                this.eatMeal(); //o anki threadin nesnesini kullanır, nullpointer var ise daha queueya eklenmemiş demektir metot geri çağrılır
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void leaving() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(getName() + getID() + " left ");
        restaurant.setTable(1); // 1 kişilik yer masa sayısına eklenir.
        System.out.println("Available table count: " + restaurant.getTable());
        isCustomerLeft = true;// run da olan while döngüsünü bitirip thread sonlandırmak için kullanılır.
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (!isCustomerLeft) {
            try {
                Thread.sleep(1222);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            available();
            if (controllerOfEnteredCustomer) {
                entered();
                try {
                    ordered();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                customerWaitingForOrder();
                eatMeal();
            }
        }
    }
}





