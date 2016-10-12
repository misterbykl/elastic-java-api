import com.google.gson.JsonArray;
import config.ApplicationConfig;
import config.ClassConfig;
import config.SpringConfig;
import elastic.ElasticManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import util.ApplicationUtil;
import util.LogUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by misterbykl
 * 11.10.2016 - 21:41
 */
public class Main {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * <p>
     * Created by misterbykl
     * 11.10.2016 - 21:41
     */
    public static void main(String[] args) throws IOException {
        ApplicationUtil.setSystemProperties();
        final Logger logger = LogUtil.getRootLogger();
        logger.info("elastic-java-api is starting...");

        AnnotationConfigApplicationContext applicationContext = null;
        try {
            applicationContext = new AnnotationConfigApplicationContext();
            applicationContext.register(ApplicationConfig.class, ClassConfig.class);
            applicationContext.refresh();
            applicationContext.registerShutdownHook();
            logger.info("elastic-java-api has started");

            ElasticManager elasticManager = SpringConfig.getBeanWithClass(ElasticManager.class);
            elasticManager.connect();
            elasticManager.search();
            elasticManager.result();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            if (applicationContext != null) {
                logger.info("App context is closing...");
                applicationContext.close();
                logger.info("App context is closed");
            }
        }
    }
}
