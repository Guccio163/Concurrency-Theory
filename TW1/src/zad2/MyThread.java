package zad2;

public class MyThread implements Runnable {

    MyVarSynch counter;
    public MyThread(MyVarSynch param) {
        counter = param;
    }

    public void run() {
        for (int i = 0; i < 100000; i++) {
            counter.add();
        }
    }
}
