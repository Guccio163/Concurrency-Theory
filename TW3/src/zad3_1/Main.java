package zad3_1;

import zad2.Guest;
import zad2.Kelner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args){

    List<Thread> threads = new ArrayList<>(5);

    Forks forks = new Forks();

        for (int i = 0; i < 5; i++) {
        Runnable r = new Philosoph(i, forks);
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