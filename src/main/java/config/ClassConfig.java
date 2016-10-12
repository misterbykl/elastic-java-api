package config;

import elastic.ElasticManager;
import json.JsonManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import util.LogUtil;

/**
 * Created by misterbykl
 * 11.10.2016 - 22:41
 */
@Configuration
public class ClassConfig {

    private static final Logger LOGGER = LogUtil.getRootLogger();

    /**
     * @return
     *
     * Created by misterbykl
     * 11.10.2016 - 22:46
     */
    @Bean
    public ElasticManager elasticManager(@Value("${elastic.ip}") String argIp,
                                         @Value("${elastic.index}") String argIndex,
                                         @Value("${elastic.size}") String argSize,
                                         @Value("${elastic.start.time}") String argStartTime,
                                         @Value("${elastic.end.time}") String argEndTime,
                                         @Value("${elastic.scroll.enable}") String argScrollEnable,
                                         @Value("${elastic.scroll.time}") String argScrollTime,
                                         @Value("${query.keys}") String argQueryKeys,
                                         @Value("${query.values}") String argQueryValues,
                                         @Value("${result.field}") String argResultField) {
        ClassConfig.LOGGER.info("ElasticManager is initialized");
        return new ElasticManager(argIp, argIndex, argSize, argStartTime, argEndTime,
                argScrollEnable, argScrollTime, argQueryKeys, argQueryValues, argResultField);
    }

    /**
     * Json manager json manager.
     *
     * @return the json manager
     *
     * Created by misterbykl
     * 12.10.2016 - 22:01
     */
    @Bean
    public JsonManager jsonManager()
    {
        ClassConfig.LOGGER.info("JsonManager is initialized");
        return new JsonManager();
    }
}
