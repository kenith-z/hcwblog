package xyz.hcworld.hcwblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动器
 * @ClassName: IndexController
 * @Author: 张红尘
 * @Date: 2020/7/11 16:15
 * @Version： 1.0
 */
@SpringBootApplication
public class HcwblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(HcwblogApplication.class, args);
        System.out.println("http://localhost:8080");
    }

}
