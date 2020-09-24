package xyz.hcworld.hcwblog.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 验证码配置类
 * @ClassName: KaptchaConfig
 * @Author: 张红尘
 * @Date: 2020/9/7 9:47
 * @Version： 1.0
 */
@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha producer () {
        Properties propertis = new Properties();
        //边框
        propertis.put("kaptcha.border", "no");
        //高度
        propertis.put("kaptcha.image.height", "38");
        //宽度
        propertis.put("kaptcha.image.width", "150");
        //颜色
        propertis.put("kaptcha.textproducer.font.color", "black");
        //32位
        propertis.put("kaptcha.textproducer.font.size", "32");
        Config config = new Config(propertis);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }

}
