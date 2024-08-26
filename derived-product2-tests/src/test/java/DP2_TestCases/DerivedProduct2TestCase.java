package DP2_TestCases;

import DP2_PageObject.DP2_HomePage;
import com.veeva.core.FileUtility.FileUtiltyClass;
import com.veeva.core.Listener.Retry;
import com.veeva.core.Logger.VeevaLog;
import com.veeva.core.Logger.VeevaLog.VeevaLogLevel;
import com.veeva.core.TestComponent.VeevaBase;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class DerivedProduct2TestCase extends VeevaBase {

    static String DP2Test_CONFIG_FILE =
            System.getProperty("user.dir") + File.separator + "src"
                    + File.separator + "test" + File.separator + "resources" + File.separator + "DerivedProduct2Data.json";
    String csvFilePath = System.getProperty("user.dir") + File.separator+ "target"+File.separator+"TestOutput"+File.separator+"DP2_FooterLink.csv";
    DP2_HomePage dp2_homePage;
    TreeSet<String> treeSet = new TreeSet<>();
    List<String> duplicateLinks = new ArrayList<>();

    public static Logger _log = Logger.getLogger(DerivedProduct2TestCase.class);
    @BeforeMethod
    public void objectCreation() throws IOException {
        String derivedProduct2Url = getTestConfigData().get("Derived_Product_2_URL").toString();
        driver.get(derivedProduct2Url);
        dp2_homePage = new DP2_HomePage(driver);
        FileUtiltyClass.checkIfFileExistAndDelete(csvFilePath);
    }

    /**
     * <b>Test Case Name :</b> derivedProduct2TestCase </br>
     * <b>Description : </b>  ●	Test Case 4: for DP2
     *                 ○	Go to DP2 home page
     *                 ○	Scroll down to the footer
     *                 ○	Different links for various categories (Team, Tickets, Shop, etc..) will be visible
     *                 ○	Find all the hyperlinks of the Footer links into a CSV file and report if any duplicate hyperlinks are present.</br>
     *
     * @author <i>Manmohan Dash</i>
     */

    @Test(retryAnalyzer = Retry.class)
    public void derivedProduct2TestCase()
        {
            VeevaLog.log(_log, "Test Case for 'derivedProduct2TestCase'  - Started ------",
                    VeevaLogLevel.info, VeevaLogLevel.reporter);
            // Accept the Cookies Alert if present
            dp2_homePage.acceptAlert();
            // Get All the Footer
            List<String> allFooterLink = dp2_homePage.getAllFooterLink();
            // Iterate each link and store into CSV file
            for (String link : allFooterLink) {
                FileUtiltyClass.writeDataToFile(csvFilePath, link);
            }
            // Now Read each link from CSV and check if any duplicate link is Present
            List<String> allLinkFromCSV = FileUtiltyClass.readDataFromFile(csvFilePath);
            assert allLinkFromCSV != null;
            for (String csvLink : allLinkFromCSV) {
                if (!treeSet.add(csvLink)) {
                    duplicateLinks.add(csvLink);
                }
            }
            // Report if any duplicate hyperlinks are present otherwise Pass the test cases
            if(!duplicateLinks.isEmpty()) {
                VeevaLog.log(_log, "Test Case Failed due to duplicate link present, Links are : "+ duplicateLinks ,
                        VeevaLogLevel.info, VeevaLogLevel.reporter);
                Assert.fail("Test Case Failed as Duplicate Link Present, Attaching the file to report");
            }else {
                VeevaLog.log(_log, "Test Case Passed as no Duplicate Link Present,No need to report",
                        VeevaLogLevel.info, VeevaLogLevel.reporter);
        }
            VeevaLog.log(_log, "Test Case for 'derivedProduct2TestCase'  - END ------",
                    VeevaLogLevel.info, VeevaLogLevel.reporter);
    }

    // Get all the TestConfigData
    public static HashMap<String, Object> getTestConfigData() throws IOException {
        return FileUtiltyClass.getJsonDataToMap(DP2Test_CONFIG_FILE);
    }

}
