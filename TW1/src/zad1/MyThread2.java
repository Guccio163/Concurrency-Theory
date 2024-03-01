package zad1;

public class MyThread2 implements Runnable {

    MyVar counter;
    public MyThread2(MyVar param) {
        counter = param;
    }

    public void run() {
        for (int i = 0; i < 100000; i++) {
            counter.subtract();
        }
    }
}
