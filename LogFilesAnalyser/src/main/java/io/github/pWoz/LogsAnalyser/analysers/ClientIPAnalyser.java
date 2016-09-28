package io.github.pWoz.LogsAnalyser.analysers;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function2;
import scala.Tuple2;

import java.util.List;

/**
 * Created by pw on 2016-09-27.
 */
public class ClientIPAnalyser implements RddAnalyser {

    private JavaRDD<String> clientIPs = null;

    public ClientIPAnalyser(JavaRDD<String> clientIPs) {
        this.clientIPs = clientIPs;
    }

    @Override
    public void analyseRdd() {
        JavaPairRDD<String, Integer> pairedIPs = clientIPs.mapToPair(ip -> new Tuple2<>(ip, 1));
        JavaPairRDD<String, Integer> summedIPs = pairedIPs.reduceByKey((x, y) -> x + y);
        JavaRDD<Tuple2<Integer, String>> swapedIPs = summedIPs.map(ip -> ip.swap());
        List<Tuple2<Integer, String>> results = swapedIPs.take(10);
        return;
    }
}
