package zad3;

public class Producer implements Runnable {
    private Buffer buffer;
    private int producerNumber;

    public Producer(Buffer buffer, int number) {
        this.buffer = buffer;
        this.producerNumber = number;
    }

    public void run() {

        for(int i = 0;  i < 5;   i++) {
            buffer.put("["+this.producerNumber+"]"+"message "+i);
        }

    }
}
