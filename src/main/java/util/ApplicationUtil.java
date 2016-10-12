package util;

import java.io.File;
import java.util.Properties;

/**
 * Created by misterbykl
 * 11.10.2016 - 22:07
 */
public class ApplicationUtil {

    private static final String APP_HOME = "ELASTIC_JAVA_API_HOME";
    private static final String LOG4J_PROPERTIES = "file:./conf/log4j2.xml";
    public static final String APPLICATION_PROPERTIES = "file:./conf/app.properties";

    /**
     * Sets system properties.
     *
     * Created by misterbykl
     * 11.10.2016 - 22:14
     */
    public static void setSystemProperties() {
        Properties prop = System.getProperties();
        prop.setProperty(LogUtil.CONTEXT_SELECTOR_KEY, LogUtil.CONTEXT_SELECTOR_VALUE);
        prop.setProperty(LogUtil.LOG4J_CONFIGURATION_FILE, ApplicationUtil.LOG4J_PROPERTIES);
    }
}
