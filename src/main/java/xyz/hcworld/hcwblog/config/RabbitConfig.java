package xyz.hcworld.hcwblog.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * RabbitMQ消息队列配置
 *
 * @ClassName: RabbitConfig
 * @Author: 张红尘
 * @Date: 2021-02-04
 * @Version： 1.0
 */
@Configuration
public class RabbitConfig {
    /**
     * ES的消息队列名
     */
    public final static String ES_QUEUE = "es_queue";
    /**
     * 直连交换机名
     */
    public final static String ES_EXCHANGE = "es_exchange";
    /**
     * 路由规则
     */
    public final static String ES_BIND_KEY = "es_bind_key";


    /**
     * 创建es的消息队列
     * @return
     */
    @Bean
    public Queue exQueue() {
        return new Queue(ES_QUEUE);
    }

    /**
     * 创建直连交换机
     * @return
     */
    @Bean
    DirectExchange exchange(){
        return new DirectExchange(ES_EXCHANGE);
    }

    /**
     * 路有绑定
     * @param exQueue 消息队列
     * @param exchange 直连交换机
     * @return
     */
    @Bean
    Binding binding(Queue exQueue,DirectExchange exchange){
        return BindingBuilder.bind(exQueue).to(exchange).with(ES_BIND_KEY);
    }



}
