package zad3_1;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Forks {
    boolean[] forks = new boolean[5];
    final Lock lock = new ReentrantLock();
    final Condition hungryPhilosoph = lock.newCondition();
    public Forks(){
        for(int i=0; i<5; i++){
            forks[i]=true;
        }
    }

    public void take(int philosophID){
        takeFork(philosophID);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        takeFork((philosophID+1)%5);
    }

    public void takeFork(int forkID){
        lock.lock();
        while(!forks[forkID]){
            try {
                hungryPhilosoph.await();
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }
        forks[forkID] = false;

        System.out.println(Arrays.toString(forks));

        lock.unlock();
    }

    public void putBack(int philosophID){
        lock.lock();
        forks[philosophID] = true;
        forks[(philosophID+1)%5] = true;
        hungryPhilosoph.signalAll();
        lock.unlock();
    }
}
