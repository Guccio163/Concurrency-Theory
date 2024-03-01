package zad2.zad2;

public class Semaphore {
    private int value;

    public Semaphore(int startVal) {
        this.value = startVal;
    }

    public synchronized void P() {
        while(value<1) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        this.value -= 1;
    }

    public synchronized void V() {
        this.value += 1;
        notifyAll();
    }
}
