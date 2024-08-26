package CP_PageObject;


import com.veeva.core.AbstractComponent.AbstractPage;
import com.veeva.core.Actions.VeevaActions;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


/**
 * <b>Name :</b> CP_HomePage </br>
 * <b>Description : </b> This Page Class hold the locator and actions item for Home page of Core Product and extends AbstractPage
 * to Inherit all the methods inside it.</br>
 *
 * @author <i>Manmohan Dash</i>
 */
public class CP_HomePage extends AbstractPage {
    public static Logger _log = Logger.getLogger(CP_HomePage.class);
    @FindBy(xpath = "//nav[@class='_nav_1d1mm_14']")
    private WebElement headerNavigation;
    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement acceptAlertPopUp;
    @FindBy(xpath = "//div[@role='dialog']//div[text()='x']")
    private WebElement dialogPopUp;
    @FindBy(xpath = "//li/a/span[contains(text(),'Shop')]")
    private WebElement shopMenuLink;

    public CP_HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
        return isPresent(shopMenuLink);
    }

    public void clickOnDialogPopUpCloseIcon() {
        waitUntilElementIsClikable(dialogPopUp);
        if (isPresent(dialogPopUp)) {
            VeevaActions.click(dialogPopUp);
        }
    }

    public void acceptAlert() {
        explicitWait(acceptAlertPopUp);
        if (isPresent(acceptAlertPopUp))
            VeevaActions.click(acceptAlertPopUp);
    }

    public CP_ShopPage clickOnShopMenuLink() {
        registerWindows();
        explicitWait(shopMenuLink);
        VeevaActions.click(shopMenuLink);
        return newPageAndSwitchToIt(CP_ShopPage.class);
    }
}
