package zad3_2;

import java.util.Random;

public class Philosoph implements Runnable{
    Forks forks;
    int ID;
    public Philosoph(int ID, Forks forks){
        this.ID = ID;
        this.forks = forks;
    }

    public void run() {
        System.out.println("["+ID+"]"+"zasiadam do stołu");
        while(true) {
            forks.take(ID);
            System.out.println("[" + ID + "]:" + "jem...");
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println("[" + ID + "]:" + "skończyłem jeść");
            forks.putBack(ID);
            try {
                Random rand = new Random();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
