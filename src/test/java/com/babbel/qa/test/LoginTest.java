package com.babbel.qa.test;


import com.babbel.qa.page.LoginPage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;


public class LoginTest extends BaseTest {

    static ExtentTest test;
    static ExtentReports report;
    private static Logger logger = LoggerFactory.getLogger(LoginTest.class);
    private static LoginPage loginPage;

    @BeforeClass(alwaysRun = true)
    public void beforeSetup() {
        report = new ExtentReports(System.getProperty("user.dir") + "/target/ExtentReportResults.html");
        loginPage = new LoginPage(appiumDriver);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        //report.endTest(test);
        //report.close();
    }

    @AfterMethod(alwaysRun = true)
    public void getResult(ITestResult result) throws IOException {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        String methodName = result.getName();

        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(LogStatus.FAIL, "Test Case Failed is "+result.getName());
            test.log(LogStatus.FAIL, "Test Case Failed is "+result.getThrowable());

            File srcFile=appiumDriver.getScreenshotAs(OutputType.FILE);
            String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/target/surefire-reports";
            FileUtils.deleteDirectory(new File(reportDirectory));
            File destFile = new File((String) reportDirectory + "/failure_screenshots/" + methodName + "_" + formater.format(calendar.getTime()) + ".png");
            FileUtils.copyFile(srcFile, destFile);

        }else if(result.getStatus() == ITestResult.SKIP){
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(LogStatus.PASS, "Testcase Passed");
        } else {
            test.log(LogStatus.SKIP, "Test Case Skipped is "+result.getName());
        }
        report.flush();
    }

    @Test(enabled = true, priority = 1, groups = {"regression"})
    public void verifyPageHeader()  {
        test = report.startTest("verifyPageHeader");
        assertTrue("Application header is not correct.", loginPage.verifyPageHeader().contains("QA"));
    }

    @Test(enabled = true, priority = 2, groups = {"regression"})
    public void verifyEmailFieldIsEnabled() {
        test = report.startTest("verifyEmailFieldIsEnabled");
        assertTrue("Email field is not enabled.", loginPage.isEmailFieldDisplayed());

    }

    @Test(enabled = true, priority = 3, groups = {"regression"})
    public void verifyEmailFieldDefaultTxt() {
        test = report.startTest("verifyEmailFieldDefaultTxt");
        assertTrue("Email field default txt is not correct.", loginPage.getEmailFieldDefaultTxt().equals("Email"));
    }

    @Test(enabled = true, priority = 4, groups = {"regression"})
    public void verifyPasswordFieldIsDisplayed() {
        test = report.startTest("verifyPasswordFieldIsDisplayed");
        assertTrue("Password field is not displayed.", loginPage.isPasswordFieldDisplayed());
    }

    @Test(enabled = true, priority = 5, groups = {"regression"})
    public void verifyPasswordFieldDefaultTxt() {
        test = report.startTest("verifyPasswordFieldDefaultTxt");
        assertTrue("Password field default txt is not correct.", loginPage.getPasswordFieldDefaultTxt().equals("Password (optional)"));
    }

    @Test(enabled = true, priority = 6, groups = {"regression"})
    public void verifyPasswordFieldShowsInputInAsterisk() {
        test = report.startTest("verifyPasswordFieldShowsInputInAsterisk");
        assertFalse("No option to verify this.Get text always return actual input. isPassword method type not available in appium.", loginPage.isPasswordTxtShownInAsterisk());
    }

    @Test(enabled = true, priority = 7, groups = {"regression"})
    public void verifySubmitButtonIsDisplayed() {
        test = report.startTest("verifySubmitButtonIsDisplayed");
        assertTrue("Submit button is not displayed.", loginPage.isSubmitButtonDisplayed());
    }

    @Test(enabled = true, priority = 8, groups = {"regression"})
    public void verifySubmitButtonLabel() {
        test = report.startTest("verifySubmitButtonLabel");
        assertTrue("Submit button Label is not correct.", loginPage.getSubmitButtonLabelTxt().contains("SIGN IN OR REGISTER"));
    }

    @Test(enabled = true, priority = 9, groups = {"sanity", "regression"})
    public void verifyLoginWithInvalidEmailId() {
        test = report.startTest("verifyLoginWithInvalidEmailId");
        loginPage.login("pdomni@gmail.com", "password");
        assertTrue("Invalid emil id not verified by the system", loginPage.isEmailFieldDisplayed());
    }

    @Test(enabled = true, priority = 10, groups = {"sanity", "regression"})
    public void verifyLoginWithInvalidPassword() {
        test = report.startTest("verifyLoginWithInvalidPassword");
        loginPage.login("pdomni@gmail.com", "password");

        assertTrue("Invalid password not verified by the system", loginPage.isPasswordFieldDisplayed());
    }

    @Test(enabled = true, priority = 11, groups = {"sanity", "regression"})
    @Parameters({"emailId", "password"})
    public void verifySuccessfulLogin(String emailId, String password) {
        test = report.startTest("verifySuccessfulLogin");
        loginPage.login(emailId, password);
        assertTrue("Login failed. Still submit buttion is displayed", loginPage.isHomePageImageDisplayed());
    }

    @Test(enabled = true, priority = 12, groups = {"sanity", "regression"})
    public void verifyBackButton() {
        test = report.startTest("verifyBackButton");
        loginPage.backButton();
        assertTrue("Android Back button is failed.", loginPage.isPasswordFieldDisplayed());
    }

    @Test(enabled = true, priority = 13, groups = {"regression"})
    public void verifyPageHeader_Negative_to_check_Screenshot()  {
        test = report.startTest("verifyPageHeader");
        assertTrue("Application header is not correct.", loginPage.verifyPageHeader().contains("QA1234543"));
    }

}