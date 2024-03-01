package zad2_1_naive;

import java.util.Random;

public class Producer implements Runnable{
    Buffer buffer;
    int M;
    int threadID;
    Chart chart;
    public Producer( Buffer buffer_, int M_, int threadID_, Chart chart_) {
        this.buffer = buffer_;
        this.M = M_;
        this.threadID = threadID_;
        this.chart = chart_;
    }

    public void run() {
        while(true) {
            int change = getRandomInt();
            long startTime = System.nanoTime();
            this.buffer.operate(change, threadID);
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
