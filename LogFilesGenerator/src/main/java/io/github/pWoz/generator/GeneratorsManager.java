package io.github.pWoz.generator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static io.github.pWoz.utils.PropertiesNames.NUMBER_OF_EXECUTORS;

/**
 * Manages generator instances
 */
public class GeneratorsManager {

    private static final Logger LOGGER = LogManager.getLogger(GeneratorsManager.class);

    public void runGenerators(Properties properties) {
        int executorsNumber = Integer.valueOf(properties.getProperty(NUMBER_OF_EXECUTORS));

        LOGGER.info("Running generators");
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < executorsNumber; i++) {
            executor.execute(new LogMessagesGenerator(properties));
        }
        LOGGER.info("Shutting down executor");
        executor.shutdown();
        try {
            LOGGER.info("Waiting for generators to stop");
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
