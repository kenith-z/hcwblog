package xyz.hcworld.hcwblog.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis扫描指定包下的Mapper<br>
 *EnableTransactionManagement开启事务
 * @ClassName: MybatisPlusConfig
 * @Author: 张红尘
 * @Date: 2020/7/12 12:52
 * @Version： 1.0
 */
@EnableTransactionManagement
@Configuration
@MapperScan(basePackages = {"xyz.hcworld.hcwblog.mapper"})
public class MybatisPlusConfig {
    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setLimit(-1);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

}
