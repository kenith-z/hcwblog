package xyz.hcworld.hcwblog.search.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.hcworld.hcwblog.config.RabbitConfig;
import xyz.hcworld.hcwblog.service.SearchService;
import xyz.hcworld.hcwblog.util.ConstantUtil;

/**
 * 文章的消息处理器<br
 * 将@RabbitListener 标注在类上面表示当有收到消息的时候，就交给 @RabbitHandler 的方法处理，具体使用哪个方法处理，根据 MessageConverter 转换后的参数类型<br
 * 监听到队列 es_queue 中有消息时则会进行接收并处理<br
 *
 *
 * @ClassName: MqMessageHandler
 * @Author: 张红尘
 * @Date: 2021-02-05
 * @Version： 1.0
 */
@Slf4j
@Component
@RabbitListener(queues = RabbitConfig.ES_QUEUE)
public class MqMessageHandler {
    /**
     * 搜索服务
     */
    @Autowired
    SearchService searchService;

    /**
     * 按添加对es进行
     * @param message
     */
    @RabbitHandler
    public void handler(PostMqIndexMessage message) {
        log.info("mq收到一条消息： {}",message.toString());
        switch (message.getType()) {
            case ConstantUtil.CREATE_OR_UPDATE:
                searchService.createOrUpdateIndex(message);
                break;
            case ConstantUtil.REMOVE:
                searchService.removeIndex(message);
                break;
            default:
                log.error("没找到对应的消息类型，请注意！！ --> {}",message.toString());
                break;
        }
    }


}
