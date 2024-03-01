package zad2_2_just;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    final Lock lock = new ReentrantLock();
    final Condition consumersCondition = lock.newCondition();
    final Condition producersCondition = lock.newCondition();

    int maxSize;
    int buffer;

    public Buffer(int M_){
        this.buffer = 0;
        this.maxSize = 2*M_;
    }

    public void operate(int change, int threadID){
        lock.lock();

        while(buffer < maxSize/2 && change<0){
            try {
                consumersCondition.await();
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }

        while(buffer > maxSize/2 && change>0){
            try {
                producersCondition.await();
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }

        buffer += change;
        System.out.println("["+threadID+"]"+"  "+buffer);

        if(buffer<maxSize/2) {
            producersCondition.signalAll();
        }else{
            consumersCondition.signalAll();
        }

        lock.unlock();

//        try {
//            Random rand = new Random();
//            int min = 1;
//            int max = 10;
//            int randomInt = rand.nextInt(max - min + 1) + min;
//            Thread.sleep(randomInt* 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }


//    public void signalProducers(){
//        lock.lock();
//        producersCondition.signalAll();
//        lock.unlock();
//    }
//
//    public void signalConsumers(){
//        lock.lock();
//        consumersCondition.signalAll();
//        lock.unlock();
//    }

}
