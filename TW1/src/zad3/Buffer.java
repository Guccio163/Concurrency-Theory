package zad3;

public class Buffer {
    private String message = "";
    private boolean isEmpty = true;
    public synchronized void put(String message){
        while(!isEmpty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        this.message = message;
        this.isEmpty = false;
        notifyAll();
    }

    public synchronized String take(){
        while(isEmpty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        String returnMessage = this.message;
        this.isEmpty = true;
        notifyAll();
        return returnMessage;
    }

}