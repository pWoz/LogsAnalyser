package io.github.pWoz.generator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;
import java.util.UUID;

import static io.github.pWoz.utils.PropertiesNames.NUMBER_OF_LOOPS_PER_EXECUTOR;

/**
 * Class that is responsible for generating log messages. Can be ran in separate thread
 */
public class LogMessagesGenerator implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(LogMessagesGenerator.class);

    private String identifier;
    private int numberOfLoops;
    private Properties properties;

    public LogMessagesGenerator(Properties properties) {
        this.properties = properties;
        readProperties();
        identifier = UUID.randomUUID().toString();
    }

    public void generateMessage(int counter) {
        LOGGER.info(identifier + ":" + counter);
    }

    public void run() {
        int i = numberOfLoops;
        while (i > 0) {
            generateMessage(i);
            i--;
        }
    }

    private void readProperties() {
        numberOfLoops = Integer.valueOf(properties.getProperty(NUMBER_OF_LOOPS_PER_EXECUTOR));
    }
}
