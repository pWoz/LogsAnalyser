package io.github.pWoz;

import io.github.pWoz.generator.GeneratorsManager;
import io.github.pWoz.utils.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 */
public class LogFilesGenerator {

    private static final Logger LOGGER = LogManager.getLogger(LogFilesGenerator.class);
    private static final String PROPERTIES_FILE_NAME = "generator.properties";

    public static void main(String[] args) {
        LOGGER.info("Log Files Generator Application started");
        Properties properties = PropertiesLoader.readProperties(PROPERTIES_FILE_NAME);

        String filename = "generator.properties";
        GeneratorsManager generatorsManager = new GeneratorsManager();
        generatorsManager.runGenerators(properties);
        LOGGER.info("Log Files Generator Application finised");
    }

}
