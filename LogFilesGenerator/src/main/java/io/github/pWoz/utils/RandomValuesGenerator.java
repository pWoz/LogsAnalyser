package io.github.pWoz.utils;

import java.util.Random;

/**
 * Generates random values for log messages
 */
public class RandomValuesGenerator {

    private int[] availableStatusCodes = {200, 201, 404, 500};
    private String[] availableApplicationNames = {"app_1", "app_2", "app_3", "app_4",};
    private String[] availableHttpMethods = {"GET", "POST", "DELETE", "HEAD"};
    private Random random = new Random();


    public int generateRandomStatusCode() {
        int index = random.nextInt(availableStatusCodes.length);
        return availableStatusCodes[index];
    }

    public String generateRandomRemoteAddress() {
        String remoteAddress = "";
        for (int i = 0; i < 4; i++) {
            if (i == 3) {
                remoteAddress += random.nextInt(254) + 1;
            } else {
                remoteAddress += random.nextInt(254) + 1 + ".";
            }

        }

        return remoteAddress;
    }

    public String generateRandomApplicationName() {
        int index = random.nextInt(availableApplicationNames.length);
        return availableApplicationNames[index];
    }

    public String generateRandomHttpmethod(){
        int index = random.nextInt(availableHttpMethods.length);
        return availableHttpMethods[index];
    }

    public int generateRandomRequestTime(){
        return random.nextInt(10000) + 1;
    }

    public String generateRandomResourcePath(){
        return "/okok/okok";
    }
}
