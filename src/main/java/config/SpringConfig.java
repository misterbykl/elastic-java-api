package config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by misterbykl
 * 11.10.2016 - 22:36
 */
public class SpringConfig implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * @param argApplicationContext
     * @throws BeansException
     *
     * Created by misterbykl
     * 11.10.2016 - 22:36
     */
    @Override
    public void setApplicationContext(ApplicationContext argApplicationContext) throws BeansException {
        SpringConfig.applicationContext = argApplicationContext;
    }

    /**
     * Gets bean with class.
     *
     * @param <T>      the type parameter
     * @param argClass the arg class
     * @return the bean with class
     * <p>
     * Created by misterbykl
     * 11.10.2016 - 23:10
     */
    public static <T> T getBeanWithClass(final Class<T> argClass) {
        return SpringConfig.applicationContext.getBean(argClass);
    }
}
