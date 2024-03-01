package zad2_2_just;


import java.util.Random;

import static zad2_2_just.Main.shouldTerminate;

public class Consumer implements Runnable{
    Buffer buffer;
    int M;
    int threadID;
    Chart chart;

    public Consumer(Buffer buffer_, int M_, int threadID_, Chart chart_) {
       this.buffer = buffer_;
       this.M = M_;
       this.threadID = threadID_;
       this.chart = chart_;
    }

    @Override
    public void run() {
        while(!shouldTerminate) {
            int change = getRandomInt();
//            System.out.println("["+threadID+"]"+" my change is  "+change);
            long startTime = System.nanoTime();
            this.buffer.operate(change*(-1), threadID);
            long endTime = System.nanoTime();
            long executionTime = endTime - startTime;
            chart.addToDataset(change, executionTime);
        }
    }

    public int getRandomInt(){
        Random rand = new Random();
        int min = 1;
        int max = M;
        return rand.nextInt(max - min + 1) + min;
    }
}
