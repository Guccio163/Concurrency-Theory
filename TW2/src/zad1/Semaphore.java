package zad1;

public class Semaphore {
    private boolean value;

    public Semaphore(boolean startVal) {
        this.value = startVal;
    }

    public synchronized void P() {
        while(!value) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        this.value = false;
    }

    public synchronized void V() {
        this.value = true;
        notifyAll();
    }
}
