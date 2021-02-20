package xyz.hcworld.hcwblog.im.handel;

import org.tio.core.ChannelContext;
import org.tio.websocket.common.WsRequest;

/**
 * @ClassName: MsgHandler
 * @Author: 张红尘
 * @Date: 2021-02-08
 * @Version： 1.0
 */
public interface MsgHandler {

    /**
     * 消息处理
     * @param data
     * @param wsRequest
     * @param channelContext
     */
    void handler(String data, WsRequest wsRequest, ChannelContext channelContext);
}
