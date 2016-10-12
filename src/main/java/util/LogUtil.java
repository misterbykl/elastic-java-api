package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by misterbykl
 * 11.10.2016 - 22:15
 */
public class LogUtil {

    private static final String EXCEPTION_LOGGER_NAME = "exceptionLogger";
    private static final String ROOT_LOGGER_NAME = "rootLogger";
    public static final String CONTEXT_SELECTOR_KEY = "Log4jContextSelector";
    public static final String CONTEXT_SELECTOR_VALUE = "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector";
    public static final String LOG4J_CONFIGURATION_FILE = "log4j.configurationFile";

    /**
     * Instantiates a new Log util.
     * <p>
     * Created by misterbykl
     * 11.10.2016 - 22:16
     */
    public LogUtil() {
    }

    /**
     * Gets logger.
     *
     * @return the logger
     * <p>
     * Created by misterbykl
     * 11.10.2016 - 22:18
     */
    private static Logger getLogger(String argLoggerName) {
        return LogManager.getLogger(argLoggerName);
    }

    /**
     * Gets root logger.
     *
     * @return the root logger
     * <p>
     * Created by misterbykl
     * 11.10.2016 - 22:18
     */
    public static Logger getRootLogger() {
        return LogUtil.getLogger(LogUtil.ROOT_LOGGER_NAME);
    }

    /**
     * Gets exception logger.
     *
     * @return the exception logger
     * <p>
     * Created by misterbykl
     * 11.10.2016 - 22:18
     */
    public static Logger getExceptionLogger() {
        return LogUtil.getLogger(LogUtil.EXCEPTION_LOGGER_NAME);
    }
}
