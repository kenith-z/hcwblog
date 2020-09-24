package xyz.hcworld.hcwblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 项目启动器
 * 启动定时器
 * @ClassName: IndexController
 * @Author: 张红尘
 * @Date: 2020/7/11 16:15
 * @Version： 1.0
 */
@EnableScheduling
@SpringBootApplication
@EnableRedisHttpSession
public class HcwblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(HcwblogApplication.class, args);
        System.out.println("http://localhost:8080");
    }

}
