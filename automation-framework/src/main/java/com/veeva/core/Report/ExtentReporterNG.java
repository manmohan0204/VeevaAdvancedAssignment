package com.veeva.core.Report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.veeva.core.TestComponent.VeevaBase;

import java.io.File;


/**
 * <b>Name :</b> ExtentReporterNG </br>
 * <b>Description : </b> This Class is used to provide Customized Extent Report </br>
 *
 * @author <i>Manmohan Dash</i>
 */
public class ExtentReporterNG {
    public static ExtentReports getReportObject() {
        String path = System.getProperty("user.dir") + File.separator
                + "target" + File.separator + "Report.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName(VeevaBase.getGlobalConfigData().get("reportName").toString());
        reporter.config().setDocumentTitle(VeevaBase.getGlobalConfigData().get("reportDocumentTile").toString());

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Tester", VeevaBase.getGlobalConfigData().get("testerName").toString());
        return extent;
    }
}
