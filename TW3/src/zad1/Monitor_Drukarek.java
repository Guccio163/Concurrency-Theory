package zad1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor_Drukarek {
    Queue<Integer> printerQueue;
    final Lock lock = new ReentrantLock();
    final Condition waitingTasks  = lock.newCondition();



    public Monitor_Drukarek(int printerCount) {
        this.printerQueue = new LinkedList<>();
        for (int i = 0; i < printerCount; i++) {
            printerQueue.add(i);
        }
    }

    public int reserve(){
        lock.lock();
        while(printerQueue.isEmpty()){
            try {
                waitingTasks.await();
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }
        int assignedPrinter = getFreePrinter();
        try {
            Thread.sleep(1000); // Uspij wątek główny na sekunde
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
        return assignedPrinter;
    }

    public void free(int printerId){
        lock.lock();
        this.printerQueue.add(printerId);
        System.out.println("printerQueue"+printerQueue);
        waitingTasks.signal();
        lock.unlock();
    }

    public synchronized int getFreePrinter(){
        return printerQueue.poll();
    }


}
