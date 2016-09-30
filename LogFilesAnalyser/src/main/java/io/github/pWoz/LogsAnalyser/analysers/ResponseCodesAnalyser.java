package io.github.pWoz.LogsAnalyser.analysers;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.util.LongAccumulator;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pw on 2016-09-22.
 */
public class ResponseCodesAnalyser implements RddAnalyser, Serializable {

    private JavaRDD<String> responseCodes = null;
    private LongAccumulator errorsCounter;
    private LongAccumulator successesCounter;

    private static final List<Integer> successResponseCodes = Arrays.asList(200, 201);
    private static final List<Integer> errorResponseCodes = Arrays.asList(404, 500);

    public ResponseCodesAnalyser(JavaRDD<String> responseCodes, LongAccumulator successesCounter, LongAccumulator errorsCounter) {
        this.responseCodes = responseCodes;
        this.successesCounter = successesCounter;
        this.errorsCounter = errorsCounter;
    }

    @Override
    public void analyseRdd() {
        countErrorResponses();
        countSuccessResponses();
        findTheMostCommonResponseTime();
        System.out.println("///Response codes////");
        System.out.println("Successes: " + successesCounter.value());
        System.out.println("Errorrs: " + errorsCounter.value());
        System.out.println("/////////////////////");
    }

    private void countErrorResponses() {
        responseCodes.foreach(responseCode -> {
            if (isErrorResponse(responseCode)) {
                errorsCounter.add(1);
            }
        });
    }

    private void countSuccessResponses() {
        responseCodes.foreach(responseCode -> {
            if (isSuccessResponse(responseCode)) {
                successesCounter.add(1);
            }
        });
    }

    private void findTheMostCommonResponseTime() {

    }

    private boolean isSuccessResponse(String responseValue) {
        return successResponseCodes.contains(Integer.parseInt(responseValue));
    }

    private boolean isErrorResponse(String responseValue) {
        return errorResponseCodes.contains(Integer.parseInt(responseValue));
    }
}
