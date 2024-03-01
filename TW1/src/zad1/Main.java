package zad1;

public class Main {
    public static void main(String[] args) {
        MyVar counter = new MyVar();
        Runnable r1 = new MyThread(counter);
        Runnable r2 = new MyThread2(counter);
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();

        try{
            t1.join();
            t2.join();
        }catch(Exception xd){
            System.out.println(xd);
        }

        System.out.println(counter.getCounter());
    }
}