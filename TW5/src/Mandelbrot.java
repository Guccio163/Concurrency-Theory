import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import javax.swing.JFrame;

// Proszę przetestować szybkość działania programu w zależności od liczby wątków w pul:
//        Ten komputer ma 10 rdzeni więc
//        jeden wątek                           (1)
//        tyle samo watkow co rdzeni            (10)
//        dwa razy wiecej wątkow niz rdzeni     (20)

//        oraz stosunku liczby zadań do liczby watkow w puli:
//        tyle samo zadan co wątków,
//        10x wiecej zadan co wątków,
//        każde zadanie to jeden piksel.

//        czyli w tym przypadku dzielimy sobie przestrzeń (dla wygody albo x albo y) na tyle części ile zadań

//        Czas należy zmierzyć 10 razy dla tego samego przypadku, policzyć średnią i odchylenie standardowe.
//        Wyniki przedstawic w tabelce, znaleźć najszybszą konfigurację.

public class Mandelbrot extends JFrame {

    private BufferedImage I;
    private static int threadCount = 20;
    private static int tasks = -1;


    public Mandelbrot() throws ExecutionException, InterruptedException {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        ExecutorService pool = Executors.newFixedThreadPool(threadCount);

        Set<Future<int[]>> setArray = new HashSet<>();
        Set<Future<int[][]>> setDoubleArray = new HashSet<>();


        int rowsPerTask = getHeight() / tasks;
        int rowsOnLastTask = getHeight() - rowsPerTask * (tasks -1);

        if(tasks == -1) {
            for (int y = 0; y < getHeight(); y++) {
                for (int x = 0; x < getWidth(); x++) {
                    Executor callable = new Executor(x, y, I);
                    Future<int[]> future = pool.submit(callable);
                    setArray.add(future);
                }
            }
        }else{
            for (int x = 0; x < getWidth(); x++) {
                for (int task=0; task<tasks-1;task++) {
                    int y1 = task*rowsPerTask;
                    int y2 = (task+1)*rowsPerTask;
                    RowExecutor callable = new RowExecutor(y1, y2, x, I);
                    Future<int[][]> future = pool.submit(callable);
                    setDoubleArray.add(future);
                }
                int y1 = getHeight()-rowsOnLastTask;
                int y2 = getHeight();
                RowExecutor callable = new RowExecutor(y1, y2, x, I);
                Future<int[][]> future = pool.submit(callable);
                setDoubleArray.add(future);
            }
        }

//        if(tasks == -1) {
//            for (Future<int[]> future : setArray) {
//                int[] point = future.get();
//                I.setRGB(point[0], point[1], point[2] | (point[2] << 8));
//            }
//        }else{
//            for (Future<int[][]> future : setDoubleArray) {
//                int[][] pointArray = future.get();
//                for(int i = 0; i<pointArray.length; i++) {
//                    int [] point = pointArray.
//                    I.setRGB(element[0], element[1], element[2] | (element[2] << 8));
//                }
//            }
//        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long[] timeArray = new long[10];
        for(int i=0; i<10; i++) {
            long poczatkowyCzas = System.currentTimeMillis();
            new Mandelbrot().setVisible(true);
            long koncowyCzas = System.currentTimeMillis();
            timeArray[i] = koncowyCzas-poczatkowyCzas;
        }
        for (long num : timeArray) {
            System.out.print(num + " ");
        }
//        System.out.println(timeArray);
    }
}
