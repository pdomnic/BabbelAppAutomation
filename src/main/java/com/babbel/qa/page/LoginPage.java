package com.babbel.qa.page;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    @AndroidFindBy(id = "com.github.fgoncalves.qa:id/email")
    MobileElement emailMobileElement;

    @AndroidFindBy(id = "com.github.fgoncalves.qa:id/password")
    MobileElement passwordMobileElement;

    @AndroidFindBy(id = "com.github.fgoncalves.qa:id/email_sign_in_button")
    MobileElement submitMobileElement;

    @AndroidFindBy(className = "android.widget.TextView")
    MobileElement headerElement;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='Picture of a house']")
    MobileElement homePageImage;

    private AppiumDriver appiumDriver;


    public LoginPage(final AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
        PageFactory.initElements(new AppiumFieldDecorator(appiumDriver), this);
    }

    public String verifyPageHeader() {
        return headerElement.getText();

    }

    public boolean isEmailFieldDisplayed() {

        if (isElementVisible(emailMobileElement, 60, appiumDriver)) {
            return emailMobileElement.isDisplayed();
        }
        return false;
    }

    public String getEmailFieldDefaultTxt() {
        return emailMobileElement.getText();
    }

    public boolean isPasswordFieldDisplayed() {

        if (isElementVisible(passwordMobileElement, 60, appiumDriver)) {
            return passwordMobileElement.isDisplayed();
        }

        return false;
    }

    public String getPasswordFieldDefaultTxt() {
        return passwordMobileElement.getText();
    }

    public boolean isPasswordTxtShownInAsterisk() {
        passwordMobileElement.sendKeys("1234567");
        String pwd = passwordMobileElement.getText();
        return pwd.contains("*****");
    }

    public boolean isSubmitButtonDisplayed() {
        return submitMobileElement.isDisplayed();
    }

    public String getSubmitButtonLabelTxt() {
        return submitMobileElement.getText();
    }

    public boolean isHomePageImageDisplayed() {
        if (isElementVisible(homePageImage, 60, appiumDriver)) {
            return homePageImage.isDisplayed();
        }
        return false;
    }

    public void login(String emailId, String password) {
        if (isElementVisible(emailMobileElement, 60, appiumDriver)) {
            this.passwordMobileElement.clear();
            this.emailMobileElement.clear();
            this.emailMobileElement.sendKeys(emailId);
            this.passwordMobileElement.sendKeys(password);
            this.submitMobileElement.click();
        }

    }

    public void backButton() {

        AndroidDriver<MobileElement> androidDriver = (AndroidDriver<MobileElement>)appiumDriver;
        androidDriver.pressKeyCode(AndroidKeyCode.BACK);

    }
}
