package xyz.hcworld.hcwblog.im.server;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.tio.common.starter.annotation.TioServerMsgHandler;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.server.handler.IWsMsgHandler;
import xyz.hcworld.hcwblog.im.handel.MsgHandler;
import xyz.hcworld.hcwblog.im.handel.MsgHandlerFactory;

import java.util.Map;

import static xyz.hcworld.hcwblog.util.ConstantUtil.*;

/**
 * websocket处理器
 *
 * @ClassName: ImWsMsghandles
 * @Author: 张红尘
 * @Date: 2021-02-08
 * @Version： 1.0
 */
@Slf4j
@TioServerMsgHandler
@Component
public class ImWsMsgHandles implements IWsMsgHandler {
    /**
     * 在握手的时候触发
     *
     * @param httpRequest
     * @param httpResponse
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        //绑定个人通道
        String userId = httpRequest.getParam("userId");
        Tio.bindUser(channelContext, userId);
        return httpResponse;
    }

    /**
     * 在握手成功后触发
     *
     * @param httpRequest
     * @param httpResponse
     * @param channelContext
     * @throws Exception
     */
    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        //绑定群聊通道,群名称叫做hcw-group-study
        Tio.bindGroup(channelContext, IM_GROUP_NAME);
    }

    /**
     * 客户端发送二进制消息触发
     *
     * @param wsRequest
     * @param bytes
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        System.out.println("接收到bytes消息");
        return null;
    }

    /**
     * 客户端发送文本消息触发
     *
     * @param wsRequest
     * @param text
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {

        Map map = JSONUtil.toBean(text, Map.class);
        String type = MapUtil.getStr(map, "type");
        String data = MapUtil.getStr(map, "data");
        MsgHandler handler = MsgHandlerFactory.getMagHandler(IM_MESS_TYPE_CHAT.equals(type)?IM_MESS_TYPE_CHAT:IM_MESS_TYPE_PING);
        //处理消息
        handler.handler(data, wsRequest, channelContext);
        return null;
    }

    /**
     * 客户端关闭连接时触发
     *
     * @param wsRequest
     * @param bytes
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }


}
