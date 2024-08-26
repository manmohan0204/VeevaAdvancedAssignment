package com.veeva.core.Logger;

import org.apache.log4j.Logger;
import org.testng.Reporter;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <b>Name :</b> VeevaLog </br>
 * <b>Description : </b> This Class is used to provide Custom Logging Methods </br>
 *
 * @author <i>Manmohan Dash</i>
 */

public class VeevaLog {

    // Default Constructor
    private VeevaLog() {
    }

    /**
     * <b>Name :</b> log </br>
     * <b>Description : </b> This method will add different level of VeevaLog to log file </br>
     *
     * @param log        The Logger Instance
     * @param logMessage The message to be logged
     * @param loglevel   The VeevaLogLevel var-args of allowed log level
     * @author <i>Manmohan Dash</i>
     */

    public static synchronized void log(final Logger log, final String logMessage, final VeevaLogLevel... loglevel) {
        if (0 == loglevel.length) {
            log.info(logMessage);
        } else {
            VeevaLogLevel[] var3 = loglevel;
            int var4 = loglevel.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                VeevaLogLevel level = var3[var5];
                switch (level) {
                    case debug:
                        log.info(logMessage);
                        log.debug(logMessage);
                        break;
                    case info:
                        log.info(logMessage);
                        break;
                    case reporter:
                        Reporter.log(logMessage, true);
                        break;
                    case warn:
                        log.warn(logMessage);
                        break;
                    case error:
                        log.error(logMessage);
                        break;
                    default:
                        log.error("Log level '" + level + "' is not supported by the Veeva Framework. ");
                }
            }
        }
    }

    /**
     * <b>Name :</b> log </br>
     * <b>Description : </b> This method will add different level of VeevaLog to log file </br>
     *
     * @param log        The Logger Instance
     * @param logMessage The message to be logged
     * @param cause      The tHrowable cause instance
     * @param logLevel   The VeevaLog var-args of allowed log level
     * @author <i>Manmohan Dash</i>
     */
    public static synchronized void log(final Logger log, final String logMessage, final Throwable cause,
                                        final VeevaLogLevel... logLevel) {
        if (0 == logLevel.length) {
            log.info(logMessage, cause);
        } else {
            VeevaLogLevel[] var5 = logLevel;
            int var6 = logLevel.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                VeevaLogLevel level = var5[var7];
                switch (level) {
                    case debug:
                        log.debug(logMessage, cause);
                        break;
                    case info:
                        log.info(logMessage, cause);
                        break;
                    case reporter:
                        log.info(logMessage, cause);
                        StringWriter stackTrace = new StringWriter();
                        stackTrace.append(logMessage);
                        if (null != cause) {
                            stackTrace.append("<br>");
                            cause.printStackTrace(new PrintWriter(stackTrace));
                        }
                        Reporter.log(stackTrace.toString(), true);
                        break;
                    case warn:
                        log.warn(logMessage, cause);
                        break;
                    case error:
                        log.error(logMessage, cause);
                        break;
                    default:
                        log.error("Log level '" + level + "' is not supported by the Veeva Framework. ");
                }
            }
        }
    }

    public static enum VeevaLogLevel {
        debug, info, reporter, warn, error;
    }


}
