package zad2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        int guestsCount = 20;

        List<Thread> threads = new ArrayList<>(guestsCount);

        Kelner kelner = new Kelner(guestsCount);

        for (int i = 0; i < guestsCount; i++) {
            Runnable r = new Guest(i, kelner);
            Thread t = new Thread(r);
            threads.add(t);
        }

        Collections.shuffle(threads);

        for (Thread t : threads){
            t.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}