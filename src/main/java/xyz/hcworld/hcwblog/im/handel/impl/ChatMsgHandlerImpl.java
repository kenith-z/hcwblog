package xyz.hcworld.hcwblog.im.handel.impl;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import xyz.hcworld.hcwblog.im.handel.MsgHandler;
import xyz.hcworld.hcwblog.im.handel.filter.ExcludeMineChannelContextFilter;
import xyz.hcworld.hcwblog.im.message.ChatImMess;
import xyz.hcworld.hcwblog.im.message.ChatOutMess;
import xyz.hcworld.hcwblog.im.vo.ImMess;
import xyz.hcworld.hcwblog.im.vo.ImTo;
import xyz.hcworld.hcwblog.im.vo.ImUser;
import xyz.hcworld.hcwblog.service.ChatService;
import xyz.hcworld.hcwblog.util.SpringUtil;

import java.util.Date;

import static xyz.hcworld.hcwblog.util.ConstantUtil.*;

/**
 * 群聊消息处理
 * @ClassName: MsgHandlerImpl
 * @Author: 张红尘
 * @Date: 2021-02-08
 * @Version： 1.0
 */
@Slf4j
public class ChatMsgHandlerImpl implements MsgHandler {
    @Override
    public void handler(String data, WsRequest wsRequest, ChannelContext channelContext) {

        ChatImMess chatImMess = JSONUtil.toBean(data, ChatImMess.class);
        ImUser mine = chatImMess.getMine();
        ImTo to = chatImMess.getTo();

        //特殊处理


        ImMess imMess = new ImMess(IM_GROUP_ID, mine.getUsername(), to.getType(), mine.getContent(), mine.getAvatar(), false, null, mine.getId(), new Date());

        ChatOutMess chatOutMess = new ChatOutMess("chatMessage", imMess);
        String mess = JSONUtil.toJsonStr(chatOutMess);
        log.info("群聊消息：----->{}",mess);
        //tio-websocket，服务器发送到客户端的packet都是WsResponse
        WsResponse wsResponse = WsResponse.fromText(mess, "utf-8");
        //过滤自己
        ExcludeMineChannelContextFilter filter = new ExcludeMineChannelContextFilter();
        filter.setCurrentContext(channelContext);
        //群发
        Tio.sendToGroup(channelContext.tioConfig, IM_GROUP_NAME, wsResponse,filter);

        //保存群聊消息
        ChatService chatService = (ChatService) SpringUtil.getBean("chatService");
        chatService.setGroupHistoryMsg(imMess);



    }
}
