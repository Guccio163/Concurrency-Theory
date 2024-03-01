package zad1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    final List<Lock> lockList = new ArrayList<>(); // list of locks for every cell (cells have to be used by
                                                    // ony one at a time but can all be accessed in the same time
    final List<Condition> conditionList = new ArrayList<>(); // one condition for every lock
    int[] numbersList; // array with numbers that Readers operate on
    int[] currentWorking; // array with reader currently working on this cell, -1 means producer -2 means consumer
    int processorsCount;

    public Buffer(int size, int processorsCount_){
        this.numbersList = new int[size];
        this.currentWorking = new int[size];
        this.processorsCount = processorsCount_;
        for(int i=0; i<size; i++){
            lockList.add(new ReentrantLock());
            conditionList.add(lockList.get(i).newCondition());
            numbersList[i] = -1;
            currentWorking[i] = -1;
        }



    }

    public void operate(int index, int readerID){
        lockList.get(index).lock();
        while(currentWorking[index] != readerID){
            try {
                conditionList.get(index).await();
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }

        try {
            Random rand = new Random();
            int min = 1;
            int max = 10;
            int randomInt = rand.nextInt(max - min + 1) + min;
            Thread.sleep(randomInt* 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(readerID == -1){
            produce(index);
        }else if(readerID == -2){
            consume(index);
        }else{
            process(index, readerID);
        }
        System.out.println(Arrays.toString(numbersList));
        lockList.get(index).unlock();
    }

    public void produce(int index){
        if(numbersList[index] == -1){
            numbersList[index] = 0;
        }
        currentWorking[index] = 0;
    }

    public void consume(int index){
        if(numbersList[index]==processorsCount) {
            System.out.println("OK: "+numbersList[index]);
        }else{
            System.out.println("WRONG: "+numbersList[index]);
        }
        numbersList[index] = -1;

        currentWorking[index] = -1;

    }


    public void process(int index, int processorID){
        if(numbersList[index] == processorID){
            numbersList[index] ++;
        }

        if(processorID == processorsCount-1){
            currentWorking[index] = -2;
        }else{
            currentWorking[index] ++;
        }


    }

    public void notifyWaiting(int index){
        lockList.get(index).lock();
        conditionList.get(index).signalAll();
        lockList.get(index).unlock();
    }

    public int getArrayLength(){
        return numbersList.length;
    }


}
