# Android App Automation 
(Appium + Java + TestNG) - TDD Framework

# Appium Installation Steps
1. brew install node 
2. npm install -g appium@1.8.1
3. brew uninstall ideviceinstaller
4. brew uninstall libimobiledevice
5. brew install --HEAD libimobiledevice
6. brew link --overwrite libimobiledevice
7. brew install --HEAD ideviceinstaller
8. brew link --overwrite ideviceinstaller
9. sudo chmod -R 777 /var/db/lockdown/      (to install the application on device)
10. npm install appium-doctor -g            (to check if system is configured correctly)

# Setup
1. Clone the code into a local machine and import the project into IntelliJ IDE and compile the project. Make sure no dependency issue.
git clone https://github.com/pdomnic/BabbelAppAutomation.git

2. Update your system config & your mobile device udid
/Users/paul.j/projects/BabbelAppAutomation/config/device.properties
/Users/paul.j/projects/BabbelAppAutomation/config/devices.json   - Update your device udid, imei, platformVersion
{
  "android": [
    {
      "udid": "L26AGGAIA4ACEEI2",
      "imei": "911358859525677",
      "systemPort": "8210",
      "deviceName": "device_2",
      "platformVersion": "7.0",
      "platformName": "android",
      "appPackage": "com.github.fgoncalves.qa",
      "appActivity": "com.github.fgoncalves.qa.MainActivity",
      "noReset": "true",
      "automationName": "uiautomator2",
      "autoGrantPermissions": "true",
      "orientation": "PORTRAIT",
      "app": "qa"
    }
  ]
}

3. How to get your device udid : Connect your device with your system. Then,
Click on the Apple icon in the top-left of the screen. 
Click on About This Mac. On the popup, tap on System Report. 
Under the hardware section, click on USB. You will notice the device connected there; click on the Android device: 
Vendor Id = Device name
Serial Number = udid

4. How to get your device imei
You can find the IMEI number and model number within the Settings of the device (steps below). You can also find the IMEI by dialling *#06# on the keypad and tapping the call button to view the IMEI.


# Run your test
<project_home>: mvn clean compile
<project_home>: mvn clean test
<project_home>: mvn test -Dgroup=regression //Run based on groups in testng.xml regression -All test cases. sanity -only functional cases.

# Reports and Failed test case screenshot
Report : /target/ExtentReportResults.html   (click ExtentReports icon on the top Left Hand Side)
Faild Case Screenshot : target/surefire-reports/failure_screenshots/
