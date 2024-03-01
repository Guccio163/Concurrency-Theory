package zad1;

//import zad2.MyThread;
//import zad2.MyThread2;
//import zad2.MyVarSynch;

public class Main {
    public static void main(String[] args) {
        Thread[] threads = new Thread[10];

        Semaphore producerSem = new Semaphore(true);
        Semaphore consumerSem = new Semaphore(false);

        Buffer counter = new Buffer(producerSem, consumerSem);


        for(int i=0; i<5; i++){
            Runnable r1 = new Producer(counter, i);
            Runnable r2 = new Consumer(counter);
            Thread t1 = new Thread(r1);
            Thread t2 = new Thread(r2);
            threads[i] = t1;
            threads[i+5] = t2;
            t1.start();
            t2.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


//        System.out.println(counter.getCounter());
    }
}