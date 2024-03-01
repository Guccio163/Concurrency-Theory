package zad3_2;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Forks {
    boolean[] forks = new boolean[5];
    int forksTaken = 0;
    final Lock lock = new ReentrantLock();
    final Condition hungryPhilosoph = lock.newCondition();
    public Forks(){
        for(int i=0; i<5; i++){
            forks[i]=true;
        }
    }

//    public void take(int philosophID){
//        lock.lock();
//        while(!forks[philosophID] || forksTaken>4){
//            try {
//                hungryPhilosoph.await();
//            }catch(InterruptedException e){
//                System.out.println(e);
//            }
//        }
//        System.out.println("Sięgam po prawy widelec"+philosophID);
//        forks[philosophID] = false;
//        while(!forks[(philosophID+1)%5]){
//            try {
//                hungryPhilosoph.await();
//            }catch(InterruptedException e){
//                System.out.println(e);
//            }
//        }
//        forks[(philosophID+1)%5] = false;
//        forksTaken++;
//        lock.unlock();
//    }

    public void take(int philosophID){
        takeFork(philosophID, true);
        System.out.println("philosoph "+philosophID+"wziął prawy");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        takeFork((philosophID+1)%5, false);
        System.out.println("philosoph "+philosophID+"wziął lewy");

    }

    public void takeFork(int forkID, boolean isFirst){
        lock.lock();
        while(!forks[forkID] || (isFirst && forksTaken>3)){
            try {
                hungryPhilosoph.await();
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }
        forks[forkID] = false;
        forksTaken++;
//        System.out.println(forksTaken);


//        System.out.println(Arrays.toString(forks));

        lock.unlock();
    }

    public void putBack(int philosophID){
        lock.lock();
        forks[philosophID] = true;
        forks[(philosophID+1)%5] = true;
        forksTaken--;
        forksTaken--;
        hungryPhilosoph.signalAll();
        lock.unlock();
    }
}
