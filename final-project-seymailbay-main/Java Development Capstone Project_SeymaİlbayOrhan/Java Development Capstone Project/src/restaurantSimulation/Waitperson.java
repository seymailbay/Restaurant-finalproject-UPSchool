package restaurantSimulation;

import restaurantSimulation.myLock.MyLock;

public class Waitperson extends MyLock implements Runnable {
    int ID;
    String name;
    Restaurant restaurant;
    Order order;
    boolean isDelivered = false;  // sipariş kontrol

    public Waitperson(int ID, String name, Restaurant restaurant) {
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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void takeOrder() {
        System.out.println(getName() + getID() + " took order ");
    }

    /**
     * Hazırlanan sipariş kuyruğu boş olduğu sürece giveCustomerOrder metodu sürekli çağırılır.
     * Şefin kuyruğa siparişi eklemesi için beklenir. (Sleep). Kuyruktan aldığımız sipariş boş değilse, hazırlanan sipariş kuyruğuna
     * ekleme yapılır.
     **/
    public void giveCustomerOrder() {
        synchronized(this) {
            try {
                Thread.sleep(1111);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            while (restaurant.preparedOrderQueue.size() <= 0) {
                giveCustomerOrder();
            }
            try {
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            order = restaurant.preparedOrderQueue.poll();
            if(order!=null){
                System.out.println(getName() + getID() + " gave order to customer " + order);
                restaurant.deliveredOrderQueue.add(order);
            }
        }
    }

    /***
     * Restoranın continueSimulation değişkeni başta true tanımlanır.False olana kadar bu döngü devam eder.
     */
    @Override
    public void run() {
        while (restaurant.continueSimulation) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            takeOrder();
            giveCustomerOrder();
        }
    }
}

