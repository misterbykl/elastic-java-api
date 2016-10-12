package elastic;

import com.google.gson.JsonArray;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import io.searchbox.core.SearchScroll;
import io.searchbox.params.Parameters;
import json.JsonManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import util.LogUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static io.searchbox.params.Parameters.SCROLL_ID;

/**
 * Created by misterbykl
 * 11.10.2016 - 22:45
 */
public class ElasticManager {

    private static final Logger LOGGER = LogUtil.getRootLogger();
    private String ip = null;
    private String index = null;
    private String size = null;
    private String startTime = null;
    private String endTime = null;
    private String scrollEnable = null;
    private String scrollTime = null;
    private String queryKeys = null;
    private String queryValues = null;
    private String resultField = null;
    private JestClient jestClient = null;

    @Autowired
    private JsonManager jsonManager;

    /**
     * Instantiates a new Elastic manager.
     *
     * @param argIp           the arg ip
     * @param argIndex        the arg index
     * @param argSize         the arg size
     * @param argStartTime    the arg start time
     * @param argEndTime      the arg end time
     * @param argScrollEnable the arg scroll enable
     * @param argScrollTime   the arg scroll time
     * @param argQueryKeys    the arg query keys
     * @param argQueryValues  the arg query values
     * @param argResultField  the arg result field
     *                        <p>
     *                        Created by misterbykl
     *                        11.10.2016 - 23:00
     */
    public ElasticManager(String argIp, String argIndex, String argSize, String argStartTime, String argEndTime,
                          String argScrollEnable, String argScrollTime, String argQueryKeys, String argQueryValues,
                          String argResultField) {
        this.ip = argIp;
        this.index = argIndex;
        this.size = argSize;
        this.startTime = argStartTime;
        this.endTime = argEndTime;
        this.scrollEnable = argScrollEnable;
        this.scrollTime = argScrollTime;
        this.queryKeys = argQueryKeys;
        this.queryValues = argQueryValues;
        this.resultField = argResultField;
    }

    /**
     * Created by misterbykl
     * 11.10.2016 - 23:00
     */
    public void connect() {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig.Builder("http://" + this.ip
                + ":9200").multiThreaded(true).readTimeout(5 * 60000).build());
        this.jestClient = factory.getObject();

        if (this.jestClient != null)
        {
            ElasticManager.LOGGER.info("connected to elasticsearch, address: http://" + this.ip + ":9200");
        }
    }

    /**
     * Search.
     * <p>
     * Created by misterbykl
     * 12.10.2016 - 00:00
     */
    public void search() {
        BoolQueryBuilder boolQueryBuilder = this.createQuery();
        JsonArray messages = null;

        if (boolQueryBuilder != null && this.jestClient != null) {
            List<String> indexArray = Arrays.asList(this.index.split(","));
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.fields(this.resultField);
            searchSourceBuilder.sort("@timestamp", SortOrder.DESC);
            searchSourceBuilder.query(boolQueryBuilder);

            messages = new JsonArray();

            if ("on".equals(this.scrollEnable)) {
                this.getResultWithScroll(this.jestClient, messages, searchSourceBuilder, indexArray);
            } else {
                this.getResultWithoutScroll(this.jestClient, messages, searchSourceBuilder, indexArray);
            }

            this.jsonManager.setJsonElements(messages);
        } else if (boolQueryBuilder == null) {
            ElasticManager.LOGGER.error("ERROR while building query");
        }
    }

    /**
     * Result.
     * <p>
     * Created by misterbykl
     * 12.10.2016 - 22:05
     */
    public void result() {
        ElasticManager.LOGGER.info(this.jsonManager.getJsonElements());
    }

    /**
     * @return BoolQueryBuilder
     * <p>
     * Created by misterbykl
     * 12.10.2016 - 01:27
     */
    private BoolQueryBuilder createQuery() {
        List<String> queryKeysArray = Arrays.asList(this.queryKeys.split(","));
        List<String> queryValuesArray = Arrays.asList(this.queryValues.split(","));
        BoolQueryBuilder boolQueryBuilder = null;

        if (queryKeysArray.size() == queryValuesArray.size()) {
            boolQueryBuilder = new BoolQueryBuilder();
            for (int i = 0; i < queryKeysArray.size(); i++) {

                boolQueryBuilder.must(QueryBuilders.queryStringQuery(queryKeysArray.get(i)).queryName(queryValuesArray.get(i)));
            }

            boolQueryBuilder.must(QueryBuilders.rangeQuery("time").gte(this.startTime)
                    .lte(this.endTime));
        } else {
            ElasticManager.LOGGER.error("ERROR: query keys and query values are not matched. query is cancelled");
        }

        return boolQueryBuilder;
    }

    /**
     * @param argClient
     * @param argMessages
     * @param argSearchSourceBuilder
     * @param argIndexArray          Created by misterbykl
     *                               12.10.2016 - 01:27
     */
    private void getResultWithScroll(JestClient argClient, JsonArray argMessages, SearchSourceBuilder argSearchSourceBuilder,
                                     List<String> argIndexArray) {
        JestResult result;
        JsonArray hits;
        Search search = new Search.Builder(argSearchSourceBuilder.toString())
                .setParameter(Parameters.SCROLL, this.scrollTime)
                .setParameter(Parameters.SIZE, this.size).addIndex(argIndexArray).build();
        try {
            result = argClient.execute(search);
            String scrollId = result.getJsonObject().get("_scroll_id").getAsString();

            while (true) {
                hits = result.getJsonObject().getAsJsonObject("hits")
                        .getAsJsonArray("hits");
                if (hits.size() > 0) {
                    for (int i = 0; i < hits.size(); i++) {
                        argMessages.add(hits.get(i).getAsJsonObject().get("fields").getAsJsonObject());
                    }
                } else {
                    break;
                }
                SearchScroll scroll = new SearchScroll.Builder(scrollId, this.scrollTime).setParameter(
                        Parameters.SIZE, this.size).build();
                result = argClient.execute(scroll);
                scrollId = result.getJsonObject().get(SCROLL_ID).getAsString();

            }
        } catch (IOException argE) {
            argE.printStackTrace();
        }
    }

    /**
     * @param argClient
     * @param argMessages
     * @param argSearchSourceBuilder
     * @param argIndexArray          Created by misterbykl
     *                               12.10.2016 - 01:27
     */
    private void getResultWithoutScroll(JestClient argClient, JsonArray argMessages, SearchSourceBuilder argSearchSourceBuilder,
                                        List<String> argIndexArray) {
        JestResult result;
        JsonArray hits;
        Search search = new Search.Builder(argSearchSourceBuilder.toString()).addIndex(argIndexArray).build();
        try {
            result = argClient.execute(search);
            hits = result.getJsonObject().getAsJsonObject("hits")
                    .getAsJsonArray("hits");
            for (int i = 0; i < hits.size(); i++) {
                argMessages.add(hits.get(i).getAsJsonObject().get("fields").getAsJsonObject());
            }
        } catch (IOException e) {
            ElasticManager.LOGGER.info("ERROR: while executing search query on Elasticsearch.\nError: " + e.getMessage());
        }
    }
}
