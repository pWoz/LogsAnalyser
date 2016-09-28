package io.github.pWoz.generator;

import io.github.pWoz.utils.LoggingMessage;
import io.github.pWoz.utils.LoggingMessageBuilder;
import io.github.pWoz.utils.RandomValuesGenerator;
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
    private RandomValuesGenerator randomValuesGenerator;

    public LogMessagesGenerator(Properties properties) {
        this.properties = properties;
        readProperties();
        randomValuesGenerator = new RandomValuesGenerator();
        identifier = randomValuesGenerator.generateRandomApplicationName();
    }

    public void generateMessage(int counter) {
        LoggingMessage message = buildMessage();

        if (message.getStatus() == 404)
            LOGGER.warn(identifier + " " + message.getMessage());
        else if (message.getStatus() == 500)
            LOGGER.error(identifier + " " + message.getMessage());
        else
            LOGGER.info(identifier + " " + message.getMessage());
    }

    private LoggingMessage buildMessage() {
        //
        LoggingMessageBuilder builder = new LoggingMessageBuilder();
        //
        LoggingMessage message = builder
                .withStatusCode(randomValuesGenerator.generateRandomStatusCode())
                .withRemoteAddr(randomValuesGenerator.generateRandomRemoteAddress())
                .withRequestTime(randomValuesGenerator.generateRandomRequestTime())
                .withResourcePath(randomValuesGenerator.generateRandomResourcePath())
                .withRequestMethod(randomValuesGenerator.generateRandomHttpMethod())
                .build();

        return message;
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
