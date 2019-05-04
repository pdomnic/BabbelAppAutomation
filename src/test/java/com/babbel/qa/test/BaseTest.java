package com.babbel.qa.test;

import com.babbel.qa.util.AppiumServerUtil;
import com.babbel.qa.util.PropertyUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    public static final String ProjectHome = System.getProperty("user.dir");
    public static PropertyUtils props = new PropertyUtils(ProjectHome + "/config/device.properties");
    public static final String APPIUM_IP_ADDRESS = props.getProperty("appiumIpAddress");
    public static final String APPIUM_PORT = props.getProperty("appiumPort");

    public static final String APPIUM_SERVER_URL = "http://" + APPIUM_IP_ADDRESS + ":" + APPIUM_PORT + "/wd/hub";

    public static final String NODE_PATH = props.getProperty("nodePath");
    public static final String APPIUM_PATH = props.getProperty("appiumPath");
    public static final String APK_PATH = ProjectHome + "/app/" + props.getProperty("android_apk");
    public static final String IPK_PATH = ProjectHome + "/app/" + props.getProperty("ios_ipk");
    public static AppiumDriver<MobileElement> appiumDriver = null;
    private static AppiumDriverLocalService appiumDriverLocalService = null;

    @BeforeSuite(alwaysRun = true)
    protected void launchTheApp() {
        try {
            appiumDriverLocalService = AppiumServerUtil.startAppiumServer(APPIUM_IP_ADDRESS, APPIUM_PORT, NODE_PATH, APPIUM_PATH);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(ProjectHome + "/config/devices.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("android");

            for (int count = 0; count < jsonArray.size(); count++) {
                JSONObject jsonObj = (JSONObject) jsonArray.get(count);
                getAppiumDriver(jsonObj);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    private AppiumDriver<MobileElement> getAppiumDriver(final JSONObject jsonObject) {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, jsonObject.get("platformName").toString());
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, jsonObject.get("platformVersion").toString());
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, jsonObject.get("deviceName").toString());
            capabilities.setCapability(MobileCapabilityType.UDID, jsonObject.get("udid").toString());
            capabilities.setCapability(MobileCapabilityType.ORIENTATION, jsonObject.get("orientation").toString());
            capabilities.setCapability(MobileCapabilityType.NO_RESET, jsonObject.get("noReset").toString());
            capabilities.setCapability(MobileCapabilityType.APP, APK_PATH);
            capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, Integer.parseInt(jsonObject.get("systemPort").toString()));
            capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, jsonObject.get("appPackage").toString());
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, jsonObject.get("automationName").toString());
            capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, jsonObject.get("appActivity").toString());
            capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, jsonObject.get("autoGrantPermissions").toString());

            appiumDriver = new AndroidDriver<MobileElement>(new URL(APPIUM_SERVER_URL), capabilities);
            appiumDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            return appiumDriver;

        } catch (Exception error) {
            error.printStackTrace();
            return null;
        }
    }

    @AfterSuite
    protected void shutdown() {
        appiumDriver.quit();
        if (appiumDriverLocalService != null) {
            AppiumServerUtil.stopAppiumProcess(appiumDriverLocalService);
        }
    }

}
