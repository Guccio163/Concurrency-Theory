package zad1;

public class MyVar {
    private int counter = 0;

    public void add(){
        counter += 1;
    }
    public void subtract(){
        counter -= 1;
    }

    public int getCounter(){
        return counter;
    }
}
