package io.github.pWoz.utils;

import io.github.pWoz.LogFilesGenerator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads properties from specified file
 *
 */
public class PropertiesLoader {

    public static Properties readProperties(String fileName) {
        Properties prop = new Properties();
        InputStream input = null;
        input = LogFilesGenerator.class.getClassLoader().getResourceAsStream(fileName);
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
