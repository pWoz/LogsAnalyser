package io.github.pWoz.LogsAnalyser.analysers;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import scala.Tuple2;

import java.util.List;

/**
 * Created by pw on 2016-09-27.
 */
public class RequestMethodAnalyser implements RddAnalyser {

    private JavaRDD<String> requestMethods = null;

    public RequestMethodAnalyser(JavaRDD<String> requestMethods) {
        this.requestMethods = requestMethods;
    }


    @Override
    public void analyseRdd() {
        JavaPairRDD<String, Integer> pairedMethods = requestMethods.mapToPair(method -> new Tuple2<>(method, 1));
        JavaPairRDD<String, Integer> reducedMethods = pairedMethods.reduceByKey((x, y) -> x + y);
        List<Tuple2<String, Integer>> results = reducedMethods.take(10);
        System.out.println("////REQUEST METHODS//");
        for (Tuple2<String, Integer> result : results) {
            System.out.println("Request method: " + result._1);
            System.out.println("Counter: " + result._2);
        }
        System.out.println("/////////////////////");
    }
}
