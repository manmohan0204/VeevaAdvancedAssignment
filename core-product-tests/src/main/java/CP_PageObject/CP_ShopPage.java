package CP_PageObject;

import com.veeva.core.AbstractComponent.AbstractPage;
import com.veeva.core.Actions.VeevaActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * <b>Name :</b> CP_ShopPage </br>
 * <b>Description : </b> This Page Class hold the locator and actions item for Shop page of Core Product and extends AbstractPage
 * to Inherit all the methods inside it.</br>
 *
 * @author <i>Manmohan Dash</i>
 */

public class CP_ShopPage extends AbstractPage {
    @FindBy(xpath = "//li[contains(@class,'top-nav-item')]/a[text()='Men']")
    private WebElement menCategoryLink;
    @FindBy(xpath = "//*[@data-trk-id='modal-close']")
    private WebElement closeIcon;

    public CP_ShopPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
        return isPresent(menCategoryLink);
    }

    public CP_CategoryPage clickOnMenCategoryLink() {
        registerWindows();
        VeevaActions.click(menCategoryLink);
        return newPageInSameTab(CP_CategoryPage.class);
    }

    public void handleDailogPopUp() {
        waitUntilElementIsClikable(closeIcon);
        if (isPresent(closeIcon)) {
            VeevaActions.click(closeIcon);
            System.out.println("Dialog Pop up closed");
        }
    }
}
