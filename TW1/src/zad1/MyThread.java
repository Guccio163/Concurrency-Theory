package zad1;

public class MyThread implements Runnable {

    MyVar counter;
    public MyThread(MyVar param) {
        counter = param;
    }

    public void run() {
        for (int i = 0; i < 100000; i++) {
            counter.add();
        }
    }
}
