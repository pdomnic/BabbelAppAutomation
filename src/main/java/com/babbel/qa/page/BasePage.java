package com.babbel.qa.page;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasePage {

    private static Logger logger = LoggerFactory.getLogger(BasePage.class);

    public boolean isElementVisible(WebElement element, int timeInSeconds, AppiumDriver appiumDriver) {

        try {
            WebDriverWait wait = new WebDriverWait(appiumDriver, timeInSeconds);
            wait.until(ExpectedConditions.visibilityOf(element));
            logger.debug("Element is visible with element name " + element);
            return true;
        } catch (Exception error) {
            error.printStackTrace();
            return false;
        }
    }

}
