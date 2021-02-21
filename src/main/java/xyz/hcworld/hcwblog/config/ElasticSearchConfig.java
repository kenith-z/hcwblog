package xyz.hcworld.hcwblog.config;


import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
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
    /**
     * es服务器访问账号
     */
    @Value("${spring.elasticsearch.username}")
    private String username;
    /**
     * es服务器访问密码
     */
    @Value("${spring.elasticsearch.password}")
    private String password;

    public  static final RequestOptions COMMON_OPTIONS;
    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        COMMON_OPTIONS = builder.build();
    }

    @Bean
    public RestHighLevelClient client() {
        HttpHost httpHost=new HttpHost(host, port);
        RestClientBuilder builder=RestClient.builder(httpHost);
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        builder.setHttpClientConfigCallback(f -> f.setDefaultCredentialsProvider(credentialsProvider));

        return new RestHighLevelClient( builder);
    }

}
