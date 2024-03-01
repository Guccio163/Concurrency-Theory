package zad2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Kelner {
    int guestsCount;
    int guestEating = guestsCount;
    int guestsEatingCount = 0;
    final Lock lock = new ReentrantLock();
    final Condition waitingGuests  = lock.newCondition();
    final Condition waitingByTable  = lock.newCondition();

    public Kelner(int guestsCount){
        this.guestsCount = guestsCount;
    }

    public void reserve(int id){
        lock.lock();
//        System.out.println("przychodzę do restauracji: "+id);
        while((guestsEatingCount != 0) && (guestEating/2 != id/2)){
            try {
                waitingGuests.await();
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }
        if(guestsEatingCount==0){
            guestEating = id;
            System.out.println("zajmuję stolik: "+id);
            guestsEatingCount++;
            try {
                waitingGuests.signalAll();
                waitingByTable.await();
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }else{
            guestsEatingCount++;
            waitingByTable.signal();
        }
        lock.unlock();
    }

    public void free() {
        lock.lock();
        guestsEatingCount--;
        if (guestsEatingCount == 0) {
            waitingGuests.signal();
        }
        lock.unlock();
    }
}
