package config;

import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import util.ApplicationUtil;
import util.LogUtil;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by misterbykl
 * 11.10.2016 - 22:29
 */
@Configuration
@PropertySource(ApplicationUtil.APPLICATION_PROPERTIES)
public class ApplicationConfig {

    private static final Logger LOGGER = LogUtil.getRootLogger();

    /**
     * Context aware spring config.
     *
     * @param argApplicationContext the arg application context
     * @return the spring config
     * <p>
     * Created by misterbykl
     * 11.10.2016 - 22:38
     */
    @Bean
    public SpringConfig contextAware(ApplicationContext argApplicationContext) {
        SpringConfig springApplicationContextAware = new SpringConfig();
        springApplicationContextAware.setApplicationContext(argApplicationContext);
        return springApplicationContextAware;
    }

    /**
     * Property config property sources placeholder configurer.
     *
     * @return the property sources placeholder configurer
     * <p>
     * Created by misterbykl
     * 11.10.2016 - 22:33
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Init.
     * <p>
     * Created by misterbykl
     * 11.10.2016 - 22:34
     */
    @PostConstruct
    public void init() {
        ApplicationConfig.LOGGER.info("Application Context is starting...");
    }

    /**
     * Destroy.
     * <p>
     * Created by misterbykl
     * 11.10.2016 - 22:34
     */
    @PreDestroy
    public void destroy() {
        ApplicationConfig.LOGGER.info("Application Context is closing...");
    }
}
