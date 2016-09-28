package io.github.pWoz.LogsAnalyser.analysers;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 * Created by pw on 2016-09-28.
 */
public class ResourcePathAnalyser implements RddAnalyser {

    private JavaRDD<String> resourcePaths = null;

    public ResourcePathAnalyser(JavaRDD<String> resourcePaths) {
        this.resourcePaths = resourcePaths;
    }

    @Override
    public void analyseRdd() {
        findMostPopularResourcePaths();
        findMostPopularApplications();
    }

    private void findMostPopularResourcePaths() {
        JavaPairRDD<String, Integer> pairedPaths = resourcePaths.mapToPair(path -> new Tuple2<>(path, 1));
        JavaPairRDD<String, Integer> summedPaths = pairedPaths.reduceByKey((x, y) -> x + y);
        JavaRDD<Tuple2<Integer, String>> swapedPaths = summedPaths.map(path -> path.swap());
        List<Tuple2<Integer, String>> results = swapedPaths.takeOrdered(10, new ResourcePathComparator());
        System.out.println("//////////Most popular resource paths///////////");
        for (Tuple2<Integer, String> result : results) {
            System.out.println("Path value: " + result._1);
            System.out.println("Counter: " + result._2);
        }
        System.out.println("/////////////////////");
    }

    private void findMostPopularApplications() {
        //TODO
    }
}


class ResourcePathComparator implements Comparator<Tuple2<Integer, String>>, Serializable {

    @Override
    public int compare(Tuple2<Integer, String> o1, Tuple2<Integer, String> o2) {
        if (o1._1 >= o2._1)
            return -1;
        else
            return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
