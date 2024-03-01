package zad2_2_just;


import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static volatile boolean shouldTerminate = false;

    public static void main(String[] args) {
        // testy odpalane w konfiguracjach 100-100 1000-1000 i 1000-10000 (processors-M)
        int processorsCount = 10;
        int M = 10;
        Chart chart = new Chart(M);

        Thread[] processorsThreads = new Thread[processorsCount];
        Buffer monitor = new Buffer(M);

        for (int i = 0; i < processorsCount/2; i++) {
            Runnable r = new Producer(monitor, M, i, chart);
            Thread t = new Thread(r);
            processorsThreads[i] = t;
            t.start();
        }
        for (int i = processorsCount/2; i < processorsCount; i++) {
            Runnable r = new Consumer(monitor, M, i, chart);
            Thread t = new Thread(r);
            processorsThreads[i] = t;
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
        int czasDoOpóźnienia = 5; // 5 sekund

        scheduler.schedule(chart::makeChart, czasDoOpóźnienia, TimeUnit.SECONDS);


        for (Thread thread : processorsThreads) {
            try {
                thread.join();
                System.out.println(thread + "  " + "joined");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
