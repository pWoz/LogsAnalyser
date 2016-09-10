import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * Main class for Log analyser
 */
public class LogAnalyser {

    private static final String LOG_FILE_NAME = "sample.txt";

    public static void main(String[] args) {
        LogAnalyser analyser  = new LogAnalyser();
        JavaSparkContext sc = analyser.initContext();
        JavaRDD<String> logFile = analyser.loadLogFile(sc);
        analyser.performAnalysis(sc);
    }

    private JavaSparkContext initContext(){
        SparkConf conf = new SparkConf().setAppName("Simple Application").setMaster("local");
        return new JavaSparkContext(conf);
    }

    private JavaRDD<String> loadLogFile(JavaSparkContext sc){
        return sc.textFile(LOG_FILE_NAME);
    }

    private void performAnalysis(JavaSparkContext sc){

    }
}
