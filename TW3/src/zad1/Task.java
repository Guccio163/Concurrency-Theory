package zad1;

public class Task implements Runnable{
    private int id;
    private int printer;
    public Monitor_Drukarek monitor;
    public Task(int id, Monitor_Drukarek monitor) {
        this.id = id;
        this.monitor = monitor;
    }

    public int getId() {
        return this.id;
    }

    public void run() {
        for(int i=0; i<3; i++) {
            this.printer = monitor.reserve();
            System.out.println("[" + id + "]:" + "drukuję na " + printer);
            try {
                Thread.sleep(3000); // Uspij wątek główny na 5 sekund
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[" + id + "]:" + "zwalniam drukarkę");
            monitor.free(printer);
        }
    }
}
