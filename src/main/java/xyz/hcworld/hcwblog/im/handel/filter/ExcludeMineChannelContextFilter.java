package xyz.hcworld.hcwblog.im.handel.filter;

import lombok.Data;
import org.tio.core.ChannelContext;
import org.tio.core.ChannelContextFilter;

/**
 * 消息过滤器
 * @ClassName: ExculedMineChannelContextFilter
 * @Author: 张红尘
 * @Date: 2021-02-09
 * @Version： 1.0
 */
@Data
public class ExcludeMineChannelContextFilter implements ChannelContextFilter {

    private ChannelContext currentContext;

    @Override
    public boolean filter(ChannelContext channelContext) {
        /**
         * 过滤当前用户不需要发送消息
         */
        if (currentContext.userid.equals(channelContext.userid)){
            return false;
        }


        return true;
    }
}
