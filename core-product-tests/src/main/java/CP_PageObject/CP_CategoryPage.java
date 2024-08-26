package CP_PageObject;


import com.veeva.core.AbstractComponent.AbstractPage;
import com.veeva.core.Actions.VeevaActions;
import com.veeva.core.Logger.VeevaLog;
import com.veeva.core.Logger.VeevaLog.VeevaLogLevel;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import static com.veeva.core.TestComponent.VeevaBase.getGlobalConfigData;


/**
 * <b>Name :</b> CP_CategoryPage </br>
 * <b>Description : </b> This Page Class hold the locator and actions item for Category page of Core Product and extends AbstractPage
 * to Inherit all the methods inside it.</br>
 *
 * @author <i>Manmohan Dash</i>
 */
public class CP_CategoryPage extends AbstractPage {

    private final static Logger _log = Logger.getLogger(String.valueOf(CP_CategoryPage.class));
    List<String> infoList;
    @FindBy(xpath = "//div[@class='product-grid-top-area']//*[@class='next-page']")
    private WebElement nextPageIcon;
    @FindBy(xpath = "//*[@class='column']")
    private List<WebElement> allProductList;
    @FindBy(css = ".product-card-title")
    private List<WebElement> productTitle;
    @FindBy(css = ".lowest .sr-only")
    private WebElement productPrice;
    @FindBy(css = ".product-vibrancy-container")
    private WebElement topLevelMessage;
    @FindBy(xpath = "//a[span[contains(text(),'Jacket')]]")
    private WebElement jacketDepartmentRadioBtn;

    public CP_CategoryPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
        return jacketDepartmentRadioBtn.isDisplayed();
    }

    public CP_CategoryPage clickOnJacketRadioBtn() {
        registerWindows();
        VeevaActions.click(jacketDepartmentRadioBtn);
        return newPageInSameTab(CP_CategoryPage.class);
    }

    public void clickPagination() {
        isElementEnabled(nextPageIcon);
        VeevaActions.click(nextPageIcon);
    }

    // This Method return the List of Product Info Like Title, Price and Top Level Message if any
    public List<String> getAllJacketProductInfo() {
        int i = 1;
        infoList = new ArrayList<>();
        while (true) {
            waitForPageToLoad(Duration.ofSeconds(Integer.parseInt(getGlobalConfigData().get("pageLoadTimeOut").toString())));
            for (WebElement product : allProductList) {
                scrollPageToElement(product);
                WebElement productTitle = getWebElementUsingJs(".product-card-title", product);
                VeevaLog.log(_log, "(" + i + ") " + productTitle.getText(), VeevaLogLevel.info, VeevaLogLevel.reporter);
                infoList.add(productTitle.getText());
                WebElement productPrice = getWebElementUsingJs(".lowest .sr-only", product);
                VeevaLog.log(_log, "(" + i + ") " + productPrice.getText(), VeevaLogLevel.info, VeevaLogLevel.reporter);
                infoList.add(productPrice.getText());
                try {
                    WebElement topLevelMessage = getWebElementUsingJs(".product-vibrancy-container", product);
                    VeevaLog.log(_log, "(" + i + ") " + topLevelMessage.getText(), VeevaLogLevel.info, VeevaLogLevel.reporter);
                    infoList.add(topLevelMessage.getText());
                } catch (Exception e) {
                    VeevaLog.log(_log, "(" + i + ") TopLevel message not found... ", VeevaLogLevel.error, VeevaLogLevel.reporter);
                }
                i++;
            }
            if (isElementEnabled(nextPageIcon)) {
                scrollPageToElement(nextPageIcon);
                VeevaActions.click(nextPageIcon);
            } else {
                break;
            }
        }
        return infoList;
    }


}
