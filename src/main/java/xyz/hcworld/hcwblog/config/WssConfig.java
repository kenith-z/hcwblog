package xyz.hcworld.hcwblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * websocket配置类
 * 开启使用STOMP协议来传输基于代理的消息
 * @ClassName: WssConfig
 * @Author: 张红尘
 * @Date: 2021-01-19
 * @Version： 1.0
 */
@EnableAsync
@Configuration
@EnableWebSocketMessageBroker
public class WssConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * 注册端点
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /*
        * addEndpoint注册一个端点，websocket的访问地址
        * SockJS如果当前浏览器不支持websocket则降级成轮询
        */
        registry.addEndpoint("/websocket").withSockJS();

    }

    /**
     * 配置消息代理点<br>
     * user代表点对点 <br>
     * topic代表广播
     * @param registry 消息代理注册表
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //推送消息的前缀，user代表点对点，topic代表广播
        registry.enableSimpleBroker("/user/","/topic/");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
