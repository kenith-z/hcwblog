package xyz.hcworld.hcwblog.im.handel.impl;

import org.tio.core.ChannelContext;
import org.tio.websocket.common.WsRequest;
import xyz.hcworld.hcwblog.im.handel.MsgHandler;

/**
 * 心跳包处理
 * @ClassName: MsgHandlerImpl
 * @Author: 张红尘
 * @Date: 2021-02-08
 * @Version： 1.0
 */
public class PingMsgHandlerImpl implements MsgHandler {
    @Override
    public void handler(String data, WsRequest wsRequest, ChannelContext channelContext) {
    }
}
