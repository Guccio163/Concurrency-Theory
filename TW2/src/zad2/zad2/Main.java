package zad2.zad2;

public class Main {
    public static void main(String[] args) {
        Thread[] threads = new Thread[15];

        Semaphore cartSem = new Semaphore(5);

//        Cart cart = new Cart(cartSem);


        for(int i=0; i<15; i++){
            Runnable r2 = new Consumer(cartSem, i);
            Thread t2 = new Thread(r2);
            threads[i] = t2;
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