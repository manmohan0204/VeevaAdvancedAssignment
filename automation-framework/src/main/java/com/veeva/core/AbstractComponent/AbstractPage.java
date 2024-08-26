package com.veeva.core.AbstractComponent;

import com.veeva.core.Actions.VeevaActions;
import com.veeva.core.Logger.VeevaLog;
import com.veeva.core.Logger.VeevaLog.VeevaLogLevel;
import org.apache.log4j.Logger;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import javax.management.ReflectionException;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.veeva.core.TestComponent.VeevaBase.getGlobalConfigData;

/**
 * <b>Name :</b> AbstractPage </br>
 * <b>Description : </b> This Abstract Class hold the reusable method used across all the pages,
 * *                    and it Inherits all the methods from VeevaActions class</br>
 *
 * @author <i>Manmohan Dash</i>
 */
public abstract class AbstractPage extends VeevaActions {

    private final static Logger _log = Logger.getLogger(String.valueOf(AbstractPage.class));
    private static final ThreadLocal<List<String>> windowHandles = new ThreadLocal<>();
    private final WebDriver driver;
    protected Duration defaultTimeout = Duration.ofSeconds(30);
    private String windowHandle;
    private AbstractPage previousPage;

    /**
     * <b>Constructor :</b> AbstractPage </br>
     * <b>Description : </b> This is Abstract Page constructor where we have defined PageFactory.initElements one time and this will be
     * * used in all the page class </br>
     *
     * @author <i>Manmohan Dash</i>
     */
    public AbstractPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * <b>Method Name :</b> isPageLoaded </br>
     * <b>Description : </b> isPageLoaded abstract method return checks if the page that implements is loaded. </br>
     *
     * @author <i>Manmohan Dash</i>
     */
    public abstract boolean isPageLoaded();

    /**
     * <b>Method Name :</b> pageRefresh </br>
     * <b>Description : </b>This method refresh the page. </br>
     *
     * @author <i>Manmohan Dash</i>
     */
    public void pageRefresh() {
        driver.navigate().refresh();
    }

    /**
     * <b>Method Name :</b> registerWindows </br>
     * <b>Description : </b>This method is used to set all register windows as a list </br>
     *
     * @author <i>Manmohan Dash</i>
     */
    public void registerWindows() {
        windowHandles.set(new ArrayList<>(driver.getWindowHandles()));
    }

    /**
     * <b>Method Name :</b> getRegisteredWindows </br>
     * <b>Description : </b>This method is used to get all register windows and return list of windows </br>
     *
     * @author <i>Manmohan Dash</i>
     */
    public List<String> getRegisteredWindows() {
        return windowHandles.get();
    }

    /**
     * <b>Method Name :</b> getDriver </br>
     * <b>Description : </b>This method is used to get the WebDriver driver</br>
     *
     * @author <i>Manmohan Dash</i>
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * <b>Method Name :</b> newPageAndSwitchToIt </br>
     * <b>Description : </b>This method is responsible for switching to new page and return the next page class</br>
     *
     * @author <i>Manmohan Dash</i>
     */
    public <T extends AbstractPage> T newPageAndSwitchToIt(Class<T> newPageclass, Duration timeout) throws ReflectionException {
        T newPage = null;
        try {
            newPage = newPageclass.getConstructor(WebDriver.class).newInstance(getDriver());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            String message = "Failed To Create new Instance of Page " + newPageclass.getSimpleName();
            VeevaLog.log(_log, message,
                    VeevaLogLevel.error, VeevaLogLevel.reporter);

            throw new ReflectionException(e, message);
        }
        newPage.setPreviousPage(this)
                .setWindowHandle(switchToNewWindow(timeout))
                .waitForPageToLoad(timeout);

        return newPage;
    }

    /**
     * <b>Method Name :</b> newPageInSameTab </br>
     * <b>Description : </b>This method is responsible for switching to new page in same tab and return the same page class</br>
     *
     * @author <i>Manmohan Dash</i>
     */
    public <T extends AbstractPage> T newPageInSameTab(Class<T> newPageclass, Duration timeout) throws ReflectionException {
        T newPage = null;
        try {
            newPage = newPageclass.getConstructor(WebDriver.class).newInstance(getDriver());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            String message = "Failed To Create new Instance of Page " + newPageclass.getSimpleName();
            VeevaLog.log(_log, message,
                    VeevaLogLevel.error, VeevaLogLevel.reporter);

            throw new ReflectionException(e, message);
        }
        newPage.setPreviousPage(this.previousPage)
                .setWindowHandle(this.windowHandle);

        return newPage.waitForPageToLoad(timeout);
    }

    public <T extends AbstractPage> T newPageInSameTab(Class<T> newPageclass) {
        try {
            return newPageInSameTab(newPageclass, defaultTimeout);
        } catch (ReflectionException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends AbstractPage> T newPageAndSwitchToIt(Class<T> newPageClass) {
        try {
            return newPageAndSwitchToIt(newPageClass, defaultTimeout);
        } catch (ReflectionException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends AbstractPage> T waitForPageToLoad() {
        return waitForPageToLoad(defaultTimeout);
    }

    /**
     * <b>Method Name :</b> waitForPageToLoad </br>
     * <b>Description : </b>This method is responsible for handling the pageLoad </br>
     *
     * @author <i>Manmohan Dash</i>
     */
    public <T extends AbstractPage> T waitForPageToLoad(Duration timeout) {
        VeevaLog.log(_log, "Waiting for Page to Load......", VeevaLogLevel.debug, VeevaLogLevel.reporter);
        try {
            new WebDriverWait(driver, timeout)
                    .ignoring(StaleElementReferenceException.class)
                    .until(d -> isPageLoaded());
        } catch (TimeoutException e) {
            String message = "Page is not Loaded..Time Out!!!!";
            VeevaLog.log(_log, message, VeevaLogLevel.error, VeevaLogLevel.reporter);

            //throw new PageNotLoadedException(message, e, driver);
        }
        VeevaLog.log(_log, "Page Loaded !! ", VeevaLogLevel.error, VeevaLogLevel.reporter);
        return (T) this;
    }

    public <T extends AbstractPage> T setWindowHandle(String windowHandle) {
        this.windowHandle = windowHandle;
        return (T) this;
    }

    public <T extends AbstractPage, U extends AbstractPage> U setPreviousPage(T previousPage) {
        this.previousPage = previousPage;
        return (U) this;
    }

    /**
     * <b>Method Name :</b> switchToNewWindow </br>
     * <b>Description : </b>This method is responsible to switch to new window and print the windowID </br>
     *
     * @author <i>Manmohan Dash</i>
     */
    public String switchToNewWindow(Duration timeOut) {
        List<String> previousWindowHadles = getRegisteredWindows();
        new WebDriverWait(driver, timeOut)
                .until(d -> d.getWindowHandles().size() > previousWindowHadles.size());
        List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
        windowHandles.removeAll(previousWindowHadles);

        String newWindowHandle = windowHandles.get(0);
        VeevaLog.log(_log, "Switching to new window opened .... '" + newWindowHandle + " ' ", VeevaLogLevel.error, VeevaLogLevel.reporter);

        driver.switchTo().window(newWindowHandle);
        return newWindowHandle;
    }

}
