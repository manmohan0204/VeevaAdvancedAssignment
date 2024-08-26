package com.veeva.core.Listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.veeva.core.Logger.VeevaLog;
import com.veeva.core.Logger.VeevaLog.VeevaLogLevel;
import com.veeva.core.Report.ExtentReporterNG;
import com.veeva.core.TestComponent.VeevaBase;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

/**
 * <b>Name :</b> Listeners </br>
 * <b>Description : </b> This Class is used to override the ITestListner methods and provide
 * implementation to that based on the execution status </br>
 *
 * @author <i>Manmohan Dash</i>
 */

public class Listeners extends VeevaBase implements ITestListener {
    private final static Logger _log = Logger.getLogger(String.valueOf(Listeners.class));
    ExtentTest test;
    ExtentReports extent = ExtentReporterNG.getReportObject();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); //Thread safe

    @Override
    public void onTestStart(ITestResult result) {
        // TODO Auto-generated method stub
        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);//unique thread id
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // TODO Auto-generated method stub
        extentTest.get().log(Status.PASS, "Test Passed");
        String file = null;
        try {
            file = attachedFileToReport(result.getMethod().getMethodName());
            extentTest.get().log(Status.INFO, "<a href = '" + file + "'>" + result.getMethod().getMethodName() + "</a>");
            VeevaLog.log(_log, "File Attached success .. for  test Case name - " + result.getMethod().getMethodName(), VeevaLogLevel.error, VeevaLogLevel.reporter);
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // TODO Auto-generated method stub
        extentTest.get().fail(result.getThrowable());//

        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
                    .get(result.getInstance());
            //Screenshot, File Attach to report
            String filePath = null;
            try {
                filePath = getScreenshot(result.getMethod().getMethodName(), driver);
                VeevaLog.log(_log, "Screenshot Captured.. for testCaseName", VeevaLogLevel.error, VeevaLogLevel.reporter);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
            VeevaLog.log(_log, "Screenshot Captured.. for  test Case name - " + result.getMethod().getMethodName(), VeevaLogLevel.error, VeevaLogLevel.reporter);
            String file = attachedFileToReport(result.getMethod().getMethodName());
            extentTest.get().log(Status.INFO, "<a href = '" + file + "'>" + result.getMethod().getMethodName() + "</a>");
            VeevaLog.log(_log, "File attached Successes.. for  test Case name - " + result.getMethod().getMethodName(), VeevaLogLevel.error, VeevaLogLevel.reporter);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStart(ITestContext context) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onFinish(ITestContext context) {
        // TODO Auto-generated method stub
        extent.flush();
    }


}
