package CP_TestCase;


import CP_PageObject.CP_CategoryPage;
import CP_PageObject.CP_HomePage;
import CP_PageObject.CP_ShopPage;
import com.veeva.core.FileUtility.FileUtiltyClass;
import com.veeva.core.Listener.Retry;
import com.veeva.core.Logger.VeevaLog;
import com.veeva.core.TestComponent.VeevaBase;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class CoreProductTestCase extends VeevaBase {

    public static Logger _log = Logger.getLogger(CoreProductTestCase.class);
    CP_HomePage cp_homePage;
    CP_ShopPage cp_shopPage;
    CP_CategoryPage cp_categoryPage;
    static String CoreTest_CONFIG_FILE =
            System.getProperty("user.dir") + File.separator + "src"
                    + File.separator + "test" + File.separator + "resources" + File.separator + "CoreProductData.json";
    String txtFilePath = System.getProperty("user.dir") + File.separator+ "target"+File.separator+"TestOutput"+File.separator+"CP_JacketInfo.txt";

    // Initialize all the object here in BeforeMethod
    @BeforeMethod
    public void objectCreation() throws IOException {
        cp_homePage = new CP_HomePage(driver);
        cp_shopPage = new CP_ShopPage(driver);
        cp_categoryPage = new CP_CategoryPage(driver);
        FileUtiltyClass.checkIfFileExistAndDelete(txtFilePath);
        String coreProductURL = getTestConfigData().get("CoreProduct_URL").toString();
        driver.get(coreProductURL);
    }


    /**
     * <b>Test Case Name :</b> coreProductTestCase </br>
     * <b>Description : </b> ●	Test Case 1: for CP
     *                 ○	From the CP home page , go to >> Shop Menu >> Men’s
     *                 ○	Find all Jackets ( from all paginated pages)
     *                 ○	Store each Jacket Price, Title and Top Seller message to a text file
     *                 ○	Attach the text file to the report</br>
     *
     * @author <i>Manmohan Dash</i>
     */

    @Test(retryAnalyzer = Retry.class)
    public void coreProductTestCase()
    {
        VeevaLog.log(_log, "Test Case for 'coreProductTestCase'  - Started ------",
                VeevaLog.VeevaLogLevel.info, VeevaLog.VeevaLogLevel.reporter);
        // Accept the Cookies Alert if present
        cp_homePage.acceptAlert();
        // Close the Sign Up pop up
        cp_homePage.clickOnDialogPopUpCloseIcon();
        // Land to Home Page and Click on Shop Page
        cp_shopPage = cp_homePage.clickOnShopMenuLink();
        // Handle the pop up for promo offer
        cp_shopPage.handleDailogPopUp();
        // Click on Men Category Link and Jacket Radio to get all the Jacket Product title, rate and toplevel message
        List<String> infoList = cp_shopPage.clickOnMenCategoryLink()
                   .clickOnJacketRadioBtn()
                   .getAllJacketProductInfo();

        // Iterate all the  Info and store into a list and write to the CSV file
        for (String info : infoList) {
            FileUtiltyClass.writeDataToFile(txtFilePath, info);
        }
        VeevaLog.log(_log, "Jacket Info Details captured to text file Completed .... : ",
                VeevaLog.VeevaLogLevel.info, VeevaLog.VeevaLogLevel.reporter);
        VeevaLog.log(_log, "Test Case for 'coreProductTestCase'  - END ------",
                VeevaLog.VeevaLogLevel.info, VeevaLog.VeevaLogLevel.reporter);
    }

    // Get all the TestConfigData
    public static HashMap<String, Object> getTestConfigData() throws IOException {
        return FileUtiltyClass.getJsonDataToMap(CoreTest_CONFIG_FILE);
    }
}
