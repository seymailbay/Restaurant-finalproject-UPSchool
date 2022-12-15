package restaurantSimulation.myLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Threadlerin senkronize çalışması için kullanılır.
 */
public class MyLock {
    public Lock lock = new ReentrantLock();

}
