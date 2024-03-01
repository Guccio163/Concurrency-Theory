package zad2_2_just;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class Chart {
    private int inputSize;
//    private final List<Long> executionTimes = new ArrayList<>();
    private int[] dataSizes;
    private long[] times;


    public Chart(int inputSize_){
        this.inputSize = inputSize_;
        this.dataSizes = new int[inputSize_];
        this.times = new long[inputSize_];
//        System.out.println(dataSizes[0]);
    }

    public void makeChart() {

        // Mierzenie czasu wykonania operacji
        // TODO do dodania (prawdopodobnie) do Producerów/Consumerów
//        for (int size : inputSizes) {
//            long startTime = System.nanoTime();
//
//            // Tutaj umieść kod, którego czas wykonania chcesz zmierzyć
//
//            long endTime = System.nanoTime();
//            long executionTime = endTime - startTime;
//            executionTimes.add(executionTime);
//        }

        // Tworzenie wykresu
        CategoryDataset dataset = createDataset(dataSizes, times);
        JFreeChart chart = ChartFactory.createBarChart(
                "Czas wykonania operacji",
                "Rozmiar danych",
                "Czas (nanosekundy)",
                dataset
        );

        // Wyświetlenie wykresu w oknie graficznym
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        javax.swing.JFrame frame = new javax.swing.JFrame();
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

    private static CategoryDataset createDataset(int[] inputSizes, long[] executionTimes) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < inputSizes.length; i++) {
            dataset.addValue(executionTimes[i], "Czas", String.valueOf(inputSizes[i]));
        }

        return dataset;
    }

    public void addToDataset(int dataSize, long time){

//        int nowyRozmiar = dataSizes.length + 1;
//        int[] nowaTablica = new int[nowyRozmiar];
//
//        System.arraycopy(dataSizes, 0, nowaTablica, 0, dataSizes.length);
//        nowaTablica[nowyRozmiar - 1] = dataSize;
//
//        dataSizes = nowaTablica;
        this.times[dataSize-1] = ((long) this.dataSizes[dataSize-1] * this.times[dataSize-1] + time)/(this.dataSizes[dataSize-1]+1);
        this.dataSizes[dataSize-1] += 1;
//        this.executionTimes.add(time);
    }

}
