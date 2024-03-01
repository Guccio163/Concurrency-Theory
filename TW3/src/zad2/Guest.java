package zad2;

import java.util.Random;

public class Guest implements Runnable{
    int guestID;
    Kelner kelner;
    public Guest(int guestID, Kelner kelner){
        this.guestID = guestID;
        this.kelner = kelner;
    }

    public void run() {
        kelner.reserve(guestID);
        System.out.println("[" + guestID + "]:" + "jem...");
        try {
            Random rand = new Random();
            int min = 1;
            int max = 10;
            int randomInt = rand.nextInt(max - min + 1) + min;
            Thread.sleep(randomInt* 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("[" + guestID + "]:" + "odchodzę od stołu");
        kelner.free();
    }
}
