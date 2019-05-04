package com.babbel.qa.util;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * This class provides methods to start & stop the appium server. Method to kill all running process. Method to check appium servier is running or not.
 *
 */

public class AppiumServerUtil {

    public static Logger logger = LoggerFactory.getLogger(AppiumServerUtil.class);

    /*
     * Use this method to start the Appium server
     */
    public static AppiumDriverLocalService startAppiumServer(final String appiumIpAddress, final String appiumPort, final String nodePath, final String appiumPath) {

        AppiumDriverLocalService appiumService = AppiumDriverLocalService.buildService(
                new AppiumServiceBuilder().
                        usingPort(Integer.parseInt(appiumPort)).
                        usingDriverExecutable(new File(nodePath)).
                        withAppiumJS(new File(appiumPath)).withIPAddress(appiumIpAddress));
        appiumService.start();

        if (appiumService.isRunning()) {
            logger.debug("Appium server starting");
        }

        long startTime = System.currentTimeMillis();
        long endTime = startTime + 3000000;

        while (!isServerRunning(false, appiumIpAddress, appiumPort)) {
            if (System.currentTimeMillis() > endTime) {
                logger.error("Server timeout exception");
            }
            logger.debug("Server has not started yet. Trying again in one second...");
            try {
                Thread.sleep(4000);
            } catch (Exception error) {
                logger.error("The exception occurred in pause method :" + error);
            }
        }

        logger.debug("Server has been started successfully.");
        return appiumService;
    }

    /*
     * Use to stop the Appium process
     */
    public static void stopAppiumProcess(final AppiumDriverLocalService appiumService) {

        if (appiumService.isRunning()) {
            appiumService.stop();
        }

        logger.debug("Appium server stopped");
    }

    /*
     * Use to stop all the process
     */
    public static void stopAllProcess() throws IOException {

        String[] command = {"/usr/bin/killall", "-KILL", "node"};
        Runtime.getRuntime().exec(command);

        logger.debug("Stopping Appium server");
    }

    /*
     * This method will return appium server is running or not.
     */
    public static boolean isServerRunning(boolean silentMode, String serverIPAddress, String serverPortNumber) {

        HttpURLConnection openConnection;

        try {
            if (!silentMode) {
                logger.debug("Checking to see if a server instance is running or not for" + serverIPAddress + " port " + serverPortNumber);
            }

            URL url = new URL("http://" + serverIPAddress + ":" + serverPortNumber + "/wd/hub/status");
            logger.debug("Appium Server URL :" + url);
            openConnection = (HttpURLConnection) url.openConnection();
            openConnection.connect();

            /**
             * If the server is up an running it will return a response message that says "OK"
             */
            logger.debug("" + openConnection.getResponseMessage());

            return ("OK").equalsIgnoreCase(openConnection.getResponseMessage());

        } catch (ConnectException error) {
            logger.error("The exception occurred in isServerRunning is : " + error);
            return false;
        } catch (Exception error) {
            logger.error("An exception was thrown." + error);
        }
        return false;
    }

}
