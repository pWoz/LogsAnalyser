package io.github.pWoz.LogsAnalyser;

import io.github.pWoz.LogsAnalyser.analysers.RddAnalyser;
import io.github.pWoz.LogsAnalyser.analysers.ResponseTimesAnalyser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.List;

/**
 * Main class for Log analyser
 */
public class LogAnalyser {

    private static final Logger LOGGER = LogManager.getLogger(LogAnalyser.class);

    private static final String LOG_FILE_NAME = "sample.txt";
    private static final int APP_NAME_POSITION = 6;
    private static final int RESPONSE_CODE_POSITION = 7;
    private static final int RESPONSE_TIME_POSITION = 8;
    private static final int CLIENT_IP_POSITION = 9;


    public static void main(String[] args) {
        LogAnalyser analyser  = new LogAnalyser();
        JavaSparkContext sc = analyser.initContext();
        JavaRDD<String> logFile = analyser.loadLogFile(sc);
        analyser.performAnalysis(sc, logFile);
    }

    private JavaSparkContext initContext(){
        SparkConf conf = new SparkConf().setAppName("Simple Application").setMaster("local");
        return new JavaSparkContext(conf);
    }

    private JavaRDD<String> loadLogFile(JavaSparkContext sc){
        return sc.textFile(LOG_FILE_NAME);
    }

    private void performAnalysis(JavaSparkContext sc, JavaRDD<String> logFile) {
        JavaRDD<Integer> responseTimes = fetchResponseTimes(logFile);
        JavaRDD<String> responseCodes  = fetchResponseCodes(logFile);
        JavaRDD<String> applicationNames = fetchApplicationNames(logFile);
        JavaRDD<String> clientIPs = fetchClientIPs(logFile);
        //
        RddAnalyser responseTimesAnalyser = new ResponseTimesAnalyser(responseTimes);
        responseTimesAnalyser.analyseRdd();
    }

    private JavaRDD<Integer> fetchResponseTimes(JavaRDD<String> logFile){
        JavaRDD<String> responseTimesWithMs = logFile.map(s -> s.split(" ")[RESPONSE_TIME_POSITION]);
        JavaRDD<Integer> responseTimes = responseTimesWithMs.map(s -> Integer.parseInt(s.substring(0, s.length() - 2)));
        LOGGER.info("Response times fetched. Sample" + responseTimes.take(5));
        return responseTimes;
    }

    private JavaRDD<String> fetchResponseCodes(JavaRDD<String> logFile){
        JavaRDD<String> responseCodes = logFile.map(s -> s.split(" ")[RESPONSE_CODE_POSITION]);
        LOGGER.info("Response codes fetched. Sample" + responseCodes.take(5));
        return responseCodes;
    }

    private JavaRDD<String> fetchApplicationNames(JavaRDD<String> logFile){
        JavaRDD<String> applicationNames = logFile.map(s -> s.split(" ")[APP_NAME_POSITION]);

        LOGGER.info("Application names fetched. Sample" + applicationNames.take(5));
        return applicationNames;
    }

    private JavaRDD<String> fetchClientIPs(JavaRDD<String> logFile){
        JavaRDD<String> clientIps = logFile.map(s -> s.split(" ")[CLIENT_IP_POSITION]);

        LOGGER.info("Client IPs fetched. Sample" + clientIps.take(5));
        return clientIps;
    }
}
