package zad1;

public class Buffer {
    private String message = "";

    Semaphore producerSem;
    Semaphore consumerSem;
    public Buffer(Semaphore producerSem, Semaphore consumerSem) {
        this.producerSem = producerSem;
        this.consumerSem = consumerSem;
    }

    public void put(String message){
        producerSem.P();
        this.message = message;
        consumerSem.V();
    }

    public String take(){
//        while(!consumerSem.getVal()) {
//            try {
//                wait();
//            } catch (InterruptedException e) {}
//        }
        consumerSem.P();
        String returnMessage = this.message;
        producerSem.V();
        return returnMessage;
    }

}