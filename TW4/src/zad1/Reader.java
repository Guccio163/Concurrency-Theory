package zad1;

import java.util.Arrays;

public class Reader implements Runnable{
    int id;
    Buffer buffer;
    public Reader(int id_, Buffer buffer_) {
       this.id = id_;
       this.buffer = buffer_;
    }

    public void run() {
        int index = 0;
        while(true) {
            this.buffer.operate(index, id);
//            System.out.println("["+this.id+"]"+"working on cell"+index);
            this.buffer.notifyWaiting(index);
            index = (index +1) % buffer.getArrayLength();
        }
    }
}
