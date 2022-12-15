package restaurantSimulation;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurant {

    public int table=5;
    public boolean continueSimulation=true;

    Queue<Order> preparingOrderQueue= new LinkedList<>();  // Hazırlanmakta olan sipariş kuyruğu
    Queue<Order> preparedOrderQueue= new LinkedList<>(); //Hazırlanan sipariş kuyruğu
    Queue<Order> deliveredOrderQueue=new LinkedList<>(); // Teslim edilen sipariş kuyruğu


    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table=this.table + table;
    }
}
