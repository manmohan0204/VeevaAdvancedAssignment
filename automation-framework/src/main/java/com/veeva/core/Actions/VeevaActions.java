package com.veeva.core.Actions;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;
import com.veeva.core.TestComponent.VeevaBase;

import java.time.*;
import java.util.*;

/**
 * <b>Name :</b> VeevaActions </br>
 * <b>Description : </b> This VeevaActions Class hold all the reusable action method used across all the pages,
 * *                        and it Inherits all the methods from VeevaBase class</br>
 *
 * @author <i>Manmohan Dash</i>
 */
public class VeevaActions extends VeevaBase {
    public static Logger _log = Logger.getLogger(VeevaActions.class);
    public WebDriverWait wait;
    WebDriver driver;
    JavascriptExecutor jse;
    Actions action;

    public VeevaActions(WebDriver driver) {
        this.driver = driver;
        jse = (JavascriptExecutor) driver;
        action = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static void click(WebElement element) {
        element.click();
    }

    public WebElement getWebElementUsingJs(String locator, WebElement element) {
        return (WebElement) jse.executeScript("return arguments[0].querySelector('" + locator + "');", element);
    }

    public List<WebElement> getListOfWebElementUsingJs(String locator, WebElement element) {
        return (List<WebElement>) jse.executeScript("return arguments[0].querySelectorAll(\"" + locator + "\");", element);
    }

    public void enterData(WebElement element, String data) {
        element.sendKeys(data);
    }

    public String getText(WebElement element) {
        return element.getText();
    }

    public boolean isPresent(WebElement ele) {
        try {
            return ele.isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    public boolean isElementEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (Exception e) {

            return false;
        }
    }

    public void explicitWait(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
        }
    }

    public void waitUntilElementIsClikable(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
        }
    }

    public void scrollPageToElement(WebElement elementToScroll) {
        if (!isPresent(elementToScroll)) {
            jse.executeScript("window.scrollBy(0,500)");
        }
    }

}

