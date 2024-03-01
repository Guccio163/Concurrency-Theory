package zad3_1;

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
//            System.out.println("[" + ID + "]:" + "jem...");
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("[" + ID + "]:" + "skończyłem jeść");
            forks.putBack(ID);
//            try {
//                Random rand = new Random();
//                int min = 1;
//                int max = 10;
//                int randomInt = rand.nextInt(max - min + 1) + min;
//                Thread.sleep(randomInt* 100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}
