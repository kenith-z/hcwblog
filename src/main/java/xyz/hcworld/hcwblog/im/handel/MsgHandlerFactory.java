package xyz.hcworld.hcwblog.im.handel;

import lombok.extern.slf4j.Slf4j;
import xyz.hcworld.hcwblog.im.handel.impl.ChatMsgHandlerImpl;
import xyz.hcworld.hcwblog.im.handel.impl.PingMsgHandlerImpl;

import java.util.HashMap;
import java.util.Map;

import static xyz.hcworld.hcwblog.util.ConstantUtil.IM_MESS_TYPE_CHAT;
import static xyz.hcworld.hcwblog.util.ConstantUtil.IM_MESS_TYPE_PING;

/**
 * IM消息处理器工厂
 * @ClassName: MsgHandlerFactoru
 * @Author: 张红尘
 * @Date: 2021-02-08
 * @Version： 1.0
 */
@Slf4j
public class MsgHandlerFactory {
    /**
     *  消息处理器列表
     */
    private static Map<String,MsgHandler> handlerMap = new HashMap<>();

    public static void init(){
        handlerMap.put(IM_MESS_TYPE_CHAT,new ChatMsgHandlerImpl());
        handlerMap.put(IM_MESS_TYPE_PING,new PingMsgHandlerImpl());
        log.info("---------------Handler factory init!---------------");
    }

    /**
     * 返回消息处理器
     * @param type 处理器的key
     * @return 有返回对应处理器，没有返回ping处理器
     */
    public static MsgHandler getMagHandler(String type){
        return handlerMap.getOrDefault(type,handlerMap.get(IM_MESS_TYPE_PING));
    }


}
