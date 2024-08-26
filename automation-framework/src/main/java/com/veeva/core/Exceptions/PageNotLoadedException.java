package com.veeva.core.Exceptions;

import org.openqa.selenium.WebDriver;

/**
 * <b>Name :</b> PageNotLoadedException </br>
 * <b>Description : </b> This Class is responsible to handle PageNot Loaded related Exception</br>
 *
 * @author <i>Manmohan Dash</i>
 */
public class PageNotLoadedException extends LoggedRuntimeException {


    public PageNotLoadedException(String message, WebDriver driver) {
        super(message, driver);
    }

    public PageNotLoadedException(String message, Throwable throwable, WebDriver driver) {
        super(message, throwable, driver);
    }
}
