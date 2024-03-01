package zad2_1_naive;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    final Lock lock = new ReentrantLock();
    final Condition waitingCondition = lock.newCondition(); // w naiwnym podejściu można po prostu budzić wszystkich
    int maxSize;
    int buffer;

    public Buffer(int M_){
        this.buffer = 0;
        this.maxSize = 2*M_;
    }

    public void operate(int change, int threadID){
        lock.lock();

        while(buffer + change < 0 || buffer + change > maxSize){
            try {
                waitingCondition.await();
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }

        buffer += change;
        System.out.println("["+threadID+"]"+"  "+buffer);
        waitingCondition.signalAll();

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
