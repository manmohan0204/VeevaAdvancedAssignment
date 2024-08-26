package com.veeva.core.TestComponent;


import com.veeva.core.FileUtility.FileUtiltyClass;
import com.veeva.core.Logger.VeevaLog;
import com.veeva.core.Logger.VeevaLog.VeevaLogLevel;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;

/**
 * <b>Name :</b> VeevaBase </br>
 * <b>Description : </b> This VeevaBase Class is responsible for all reusable method for test level
 * *                    like initializing the driver, generic method for generating screenshot, attaching
 * *                    a file to ExtentReport getJsonConfig data and more, and this will be extended to all Test Cases </br>
 *
 * @author <i>Manmohan Dash</i>
 */
public class VeevaBase {

    private final static Logger _log = Logger.getLogger(String.valueOf(VeevaBase.class));
    static String global_CONFIG_FILE =
            System.getProperty("user.dir") + File.separator + "src"
                    + File.separator + "main" + File.separator + "resources" + File.separator + "globalConfig.json";

    public WebDriver driver;

    // Get all the GlobalConfigData
    public static HashMap<String, Object> getGlobalConfigData() {
        try {
            return FileUtiltyClass.getJsonDataToMap(global_CONFIG_FILE);
        } catch (IOException e) {
            return null;
        }
    }

    // Initialize the Driver  and activate the browser Driver
    public WebDriver initializeDriver(String browserName) throws Exception {
        driver = DriverFactory.getInstance().getDriver(browserName.toUpperCase());
        return driver;
    }

    // This method is responsible to Capture the Screenshot
    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        String screenShotFolderPath = System.getProperty("user.dir") + File.separator + "target" + File.separator + "Screenshots" + File.separator + testCaseName + ".png";
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(screenShotFolderPath);
        FileUtils.copyFile(source, file);
        return screenShotFolderPath;
    }

    // This method is responsible to attached the file based on the testcase demand
    public String attachedFileToReport(String testCaseName) throws IOException {
        String filePath = null;
        if (testCaseName.toLowerCase().contains("derived")) {
            filePath = System.getProperty("user.dir") + File.separator + "target" + File.separator + "TestOutput" + File.separator + "DP2_FooterLink.csv";
        } else if (testCaseName.toLowerCase().contains("core")) {
            filePath = System.getProperty("user.dir") + File.separator + "target" + File.separator + "TestOutput" + File.separator + "CP_JacketInfo.txt";
        }
        return filePath;
    }

    @Parameters({"browser"})
    @BeforeTest(alwaysRun = true)
    public void setUpTest(String browser) throws Exception {
        driver = initializeDriver(browser);
        VeevaLog.log(_log, "Driver Activated... Browser Opened :::: " + browser, VeevaLogLevel.info, VeevaLogLevel.reporter);
        driver.manage().deleteAllCookies();
        /*int width = Integer.parseInt(getGlobalConfigData().get("windowWidth").toString());
        int height = Integer.parseInt(getGlobalConfigData().get("windowHeight").toString());
        driver.manage().window().setSize(new Dimension(width,height));//full screen*/
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Parameters({"browser"})
    @AfterTest(alwaysRun = true)
    public void tearDown(String browser) throws Exception {
        driver = initializeDriver(browser);
        if (driver != null) {
            driver.quit();
        }
    }
}

