package xyz.hcworld.hcwblog.config;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * es配置
 * @ClassName: ElasticSearchConfig
 * @Author: 张红尘
 * @Date: 2021-02-01
 * @Version： 1.0
 */
@Configuration
public class ElasticSearchConfig {
    /**
     * es服务器ip
     */
    @Value("${spring.elasticsearch.host}")
    private String host;
    /**
     * es服务器端口
     */
    @Value("${spring.elasticsearch.port}")
    private int port;

    public  static final RequestOptions COMMON_OPTIONS;
    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        COMMON_OPTIONS = builder.build();
    }

    @Bean
    public RestHighLevelClient client() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(host, port)));
    }

}
