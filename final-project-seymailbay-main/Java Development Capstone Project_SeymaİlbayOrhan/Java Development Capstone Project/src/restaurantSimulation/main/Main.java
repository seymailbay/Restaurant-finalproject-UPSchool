package restaurantSimulation.main;


import restaurantSimulation.Chef;
import restaurantSimulation.Customer;
import restaurantSimulation.Restaurant;
import restaurantSimulation.Waitperson;


public class Main {

    public static void main(String[] args) throws InterruptedException {



        Restaurant restaurantData = new Restaurant();  // Restoranın içerisindeki bilgilere erişebilmek için restoran nesnesi oluşturulur.
        int customerCount = 9;
        int waitpersonCount = 3;
        int chefCount = 2;
        System.out.println("Simulation started...\n"+"Customer: "+customerCount+" | Waitperson: " + waitpersonCount+ " | Chef: " +chefCount + " | Table: " + restaurantData.getTable());
        System.out.println();


        /**
         *          CUSTOMER THREADS
         */
        Thread[] customerThreads = new Thread[customerCount];
        for (int j = 0; j < customerCount; j++) {
            customerThreads[j] = new Thread(new Customer(j+1, "Customer", restaurantData));
            customerThreads[j].start();
            Thread.sleep(1000);

        }

        /**
         *         WAITPERSON THREADS
         */
            Thread[] waitpersonThreads = new Thread[waitpersonCount];
            for (int i = 0; i < waitpersonCount; i++) {
                waitpersonThreads[i] = new Thread(new Waitperson(i+1, "Waitperson", restaurantData));
                waitpersonThreads[i].start();
                Thread.sleep(1000);
            }
        /**
         *          CHEF THREADS
         */
            Thread[] chefThreads = new Thread[chefCount];
            for (int i = 0; i < chefCount; i++) {
                chefThreads[i] = new Thread(new Chef(i+1, "Chef ", restaurantData));
                chefThreads[i].start();
                Thread.sleep(250);

           }
        }
}