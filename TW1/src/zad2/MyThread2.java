package zad2;

public class MyThread2 implements Runnable {

    MyVarSynch counter;
    public MyThread2(MyVarSynch param) {
        counter = param;
    }

    public void run() {
        for (int i = 0; i < 100000; i++) {
            counter.subtract();
        }
    }
}
