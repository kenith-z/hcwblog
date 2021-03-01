package xyz.hcworld.hcwblog.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 百度内容识别api配置文件
 * @ClassName: BaiduAipConfig
 * @Author: 张红尘
 * @Date: 2021-03-01
 * @Version： 1.0
 */
@Data
@Configuration
public class BaiduAipConfig {
    @Value("${aip.baidu.app-id}")
    private String appId ;
    @Value("${aip.baidu.app-key}")
    private  String appKey;
    @Value("${aip.baidu.secret-key}")
    private  String secretKey;


}
