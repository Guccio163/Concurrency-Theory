package zad2_1_naive;


import zad2_1_naive.Chart;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    // testy odpalane w konfiguracjach 100-100 1000-1000 i 1000-10000 (processors-M)
    public static void main(String[] args) {

        int processorsCount = 100;
        int M = 10000;
        Chart chart = new Chart(M);


        Thread[] precessorsThreads = new Thread[processorsCount];
        Buffer monitor = new Buffer(M);

        for (int i = 0; i < processorsCount/2; i++) {
            Runnable r = new Producer(monitor, M, i, chart);
            Thread t = new Thread(r);
            precessorsThreads[i] = t;
            t.start();
        }
        for (int i = processorsCount/2; i < processorsCount; i++) {
            Runnable r = new Consumer(monitor, M, i, chart);
            Thread t = new Thread(r);
            precessorsThreads[i] = t;
            t.start();
        }

//        int czasDoZakonczenia = 5000; // czas w milisekundach (np. 5000 = 5 sekund)
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                shouldTerminate = true;
//            }
//
//        }, czasDoZakonczenia);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        int czasDoOpóźnienia = 10; // 5 sekund

        scheduler.schedule(chart::makeChart, czasDoOpóźnienia, TimeUnit.SECONDS);

        for (Thread thread : precessorsThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
