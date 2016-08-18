package io.github.pWoz;

import io.github.pWoz.generator.GeneratorsManager;
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

    public static void main(String[] args) {
        LOGGER.info("Log Files Generator Application started");
        Properties properties = readProperties();
        GeneratorsManager generatorsManager = new GeneratorsManager();
        generatorsManager.runGenerators(properties);
        LOGGER.info("Log Files Generator Application finised");
    }

    private static Properties readProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        //
        String filename = "generator.properties";
        input = LogFilesGenerator.class.getClassLoader().getResourceAsStream(filename);
        //
        try {
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }
}
