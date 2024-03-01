package zad2.zad2;

public class Consumer implements Runnable {
    private Semaphore cartSem;
    private int id;

    public Consumer(Semaphore cartSem, int i) {
        this.cartSem = cartSem;
        this.id = i;
    }

    public void run() {

        cartSem.P();
        System.out.println("["+ id + "]:"+ "wziąłem koszyk");
        try {
            Thread.sleep(5000); // Uspij wątek główny na 5 sekund
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("["+ id + "]:"+ "oddałem koszyk");
        cartSem.V();
        }

}
