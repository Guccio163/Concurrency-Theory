package zad1;

public class Main {

    public static void main(String[] args) {

        int processorsCount = 10;
        int bufferSize = 10;


        Thread[] precessorsThreads = new Thread[processorsCount];
        Buffer monitor = new Buffer(bufferSize, processorsCount);

        for (int i = 0; i < processorsCount; i++) {
            Runnable r = new Reader(i, monitor);
            Thread t = new Thread(r);
            precessorsThreads[i] = t;
            t.start();
        }

        Runnable r1 = new Reader(-2, monitor);
        Thread producerThread = new Thread(r1);
        producerThread.start();

        Runnable r2 = new Reader( -1, monitor);
        Thread consumerThread = new Thread(r2);
        consumerThread.start();

        for (Thread thread : precessorsThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            producerThread.join();
            consumerThread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
