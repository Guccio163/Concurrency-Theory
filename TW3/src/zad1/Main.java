package zad1;

public class Main {

    public static void main(String[] args) {

        Thread[] threads = new Thread[15];

        Monitor_Drukarek monitor = new Monitor_Drukarek(5);

        for (int i = 0; i < 15; i++) {
            Runnable r = new Task(i, monitor);
            Thread t = new Thread(r);
            threads[i] = t;
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