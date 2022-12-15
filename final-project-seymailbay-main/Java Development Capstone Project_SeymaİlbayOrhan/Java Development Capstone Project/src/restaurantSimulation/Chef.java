package restaurantSimulation;

import restaurantSimulation.myLock.MyLock;

public class Chef extends MyLock implements Runnable {

    int ID;
    String name;
    Restaurant restaurant;
    Order order;
    boolean isCookCompleted=false;

    public Chef(int ID,String name, Restaurant restaurant) {
        this.ID=ID;
        this.name = name;
        this.restaurant=restaurant;
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

    public void receivedOrder(){
        System.out.print(getName() + getID() +" received order. ");
        startedCooking();
    }
    public void startedCooking(){
        System.out.println(getName()+ getID() +" started to cook.");
    }
    /**
     * Hazırlanmaya gönderilen sipariş kuyruğu boş olduğu sürece tekrar completedCooking başlatılır.
     * Customer siparişi verdikten sonra hazırlanan sipariş kuyruğuna eklenir.
     * Siparişin boş olup olmadığı kontrol edilir.Eğer boş değil ise receivedOrder çağırılır ve hazırlanma süresi kadar şef uyutulur.
     * Eğer sipariş null ise döngü sonlandırılır.
     **/
    public synchronized void completedCooking() throws InterruptedException {
        try {
            while (restaurant.preparingOrderQueue.size() <= 0) {
                Thread.sleep(111);
                completedCooking();
            }
        } catch (InterruptedException e) {
            completedCooking();
        } finally {
            order=restaurant.preparingOrderQueue.poll();
            restaurant.preparedOrderQueue.add(order);
            if (order!=null){
                receivedOrder();
                Thread.sleep(order.totalPreparationTime);
                System.out.print(getName() + getID() + " completed cooking: ");
                System.out.println(order);
            }else{
                restaurant.continueSimulation=false;
                return;
            }
        }
    }

    @Override
    public void run() {
        while(restaurant.continueSimulation){
            try {
                completedCooking();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
