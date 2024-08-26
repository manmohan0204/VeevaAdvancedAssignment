package com.veeva.core.Exceptions;

import org.openqa.selenium.WebDriver;

/**
 * <b>Name :</b> LoggedRuntimeException </br>
 * <b>Description : </b> This Class is responsible to handle Runtime related Exception</br>
 *
 * @author <i>Manmohan Dash</i>
 */
public class LoggedRuntimeException extends RuntimeException {

    public LoggedRuntimeException(String message, WebDriver driver) {
        super(message);
    }

    public LoggedRuntimeException(String message, Throwable throwable, WebDriver driver) {
        super(message, throwable);
    }

}
