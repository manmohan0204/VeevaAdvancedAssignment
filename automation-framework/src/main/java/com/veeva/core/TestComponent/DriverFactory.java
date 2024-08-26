package com.veeva.core.TestComponent;

import com.veeva.core.AbstractComponent.AbstractPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * <b>Name :</b> DriverFactory </br>
 * <b>Description : </b> This DriverFactory Class responsible for deciding to invoke driver based on user choice</br>
 *
 * @author <i>Manmohan Dash</i>
 */
public class DriverFactory {

    private final static Logger _log = Logger.getLogger(String.valueOf(AbstractPage.class));
    private static final DriverFactory instance = new DriverFactory();
    private static final ThreadLocal<WebDriver> threadLocal = new ThreadLocal<WebDriver>();

    private DriverFactory() {

    }

    public static DriverFactory getInstance() {
        return instance;
    }

    public WebDriver getDriver(String type) throws Exception {
        if (threadLocal.get() == null) {
            switch (type) {
                case "CHROME":
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--incognito");
                    threadLocal.set(new ChromeDriver(options));
                    break;
                case "FIREFOX":
                    threadLocal.set(new FirefoxDriver());
                    break;
                case "EDGE":
                    threadLocal.set(new EdgeDriver());
                    break;
                default:
                    throw new Exception("Provided Browser Type: '" + type + "' is not supported or" +
                            " should not be empty, please specify the proper browser name");
            }
        }
        return threadLocal.get();
    }

}
