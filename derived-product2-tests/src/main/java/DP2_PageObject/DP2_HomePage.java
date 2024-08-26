package DP2_PageObject;

import com.veeva.core.AbstractComponent.AbstractPage;
import com.veeva.core.Actions.VeevaActions;
import com.veeva.core.Logger.VeevaLog;
import com.veeva.core.Logger.VeevaLog.VeevaLogLevel;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Name :</b> DP2_HomePage </br>
 * <b>Description : </b> This Page Class hold the locator and actions item for Home page of Derived Product2 and extends AbstractPage
 * to Inherit all the methods inside it.</br>
 *
 * @author <i>Manmohan Dash</i>
 */

public class DP2_HomePage extends AbstractPage {
    public static Logger _log = Logger.getLogger(DP2_HomePage.class);
    @FindBy(tagName = "footer")
    private WebElement footer;
    @FindBy(xpath = "//nav[contains(@class, 'Footer')]")
    private List<WebElement> footerItemList;
    @FindBy(css = "[data-testid='footer-list-item'] > a")
    private List<WebElement> footerLinkList;
    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement acceptAlertPopUp;

    public DP2_HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
        return isPresent(footer);
    }

    // Get all the Footer Link and return in a list form
    public List<String> getAllFooterLink() {

        explicitWait(footer);
        scrollPageToElement(footer);
        VeevaLog.log(_log, "Printing all Link", VeevaLogLevel.error, VeevaLogLevel.reporter);
        List<String> allLinks = new ArrayList<>();
        List<WebElement> footerLinks = getListOfWebElementUsingJs("[data-testid='footer-list-item'] > a", footer);
        int j = 1;
        for (WebElement link : footerLinks) {
            String href = link.getAttribute("href");
            System.out.println("(" + j + ") " + href);
            allLinks.add(href);
            j++;
        }
        return allLinks;
    }

    // Handle Accept the Cookie Alert if Displayed
    public void acceptAlert() {
        explicitWait(acceptAlertPopUp);
        if (isPresent(acceptAlertPopUp))
            VeevaActions.click(acceptAlertPopUp);
    }


}
