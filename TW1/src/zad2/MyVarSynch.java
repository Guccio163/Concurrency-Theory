package zad2;

public class MyVarSynch {
    private int counter = 0;

    public synchronized void add(){
        counter += 1;
    }
    public synchronized void subtract(){
        counter -= 1;
    }

    public synchronized int getCounter(){
        return counter;
    }
}
