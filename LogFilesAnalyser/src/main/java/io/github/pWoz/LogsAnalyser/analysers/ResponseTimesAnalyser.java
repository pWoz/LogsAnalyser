package io.github.pWoz.LogsAnalyser.analysers;

import org.apache.spark.api.java.JavaRDD;

/**
 * Created by pw on 2016-09-22.
 */
public class ResponseTimesAnalyser implements RddAnalyser{

    private JavaRDD<Integer> responseTimes = null;

    public ResponseTimesAnalyser(JavaRDD<Integer> responseTimes) {
        this.responseTimes = responseTimes;
    }

    @Override
    public void analyseRdd() {
        long biggestValue = findBiggestValue();
        long smallestValue = findSmallestValue();
        double meanValue = findMeanValue();
        System.out.println("/////////////////////");
        System.out.println("Biggest: " + biggestValue);
        System.out.println("Smallest: " + smallestValue);
        System.out.println("Mean value: " + meanValue);
        System.out.println("/////////////////////");
    }

    private long findBiggestValue() {
        return responseTimes.reduce((a, b) -> Math.max(a, b));
    }

    private long findSmallestValue() {
        return responseTimes.reduce((a, b) -> Math.min(a, b));
    }

    private double findMeanValue() {
        long sum = responseTimes.reduce((a, b) -> a + b);
        long rddSize = responseTimes.count();
        return sum / rddSize;
    }

}