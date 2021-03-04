package xyz.hcworld.hcwblog.util;

import java.util.Map;

/**
 * 系统常量
 * @ClassName: ConstantUtil
 * @Author: 张红尘
 * @Date: 2021-01-11
 * @Version： 1.0
 */
public class ConstantUtil {

    /**
     * AJAX请求标志
     */
    public static  Integer PAGE_SIZE = 10;
    /**
     * AJAX请求标志
     */
    public static final String XRW = "XMLHttpRequest";
    /**
     * 前端的AJAX请求标志
     */
    public static final String H_XRW = "X-Requested-With";
    /**
     * 验证码
     */
    public static final String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";
    /**
     * admin的id
     */
    public static final Long ADMIN_ID = 1L;
    /**
     * admin的id
     */
    public static final Integer ADMIN_WEIGHT = 100;
    /**
     * 添加收藏
     */
    public static final String ADD = "add";
    /**
     * 删除
     */
    public static final String REMOVE = "remove";
    /**
     * 置顶
     */
    public static final String STICK = "stick";
    /**
     * 加精
     */
    public static final String STATUS = "status";
    /**
     * mq操作类型
     */
    public static  final String CREATE_OR_UPDATE = "create_update";
    /**
     * im消息类型 心跳
     */
    public static final String IM_MESS_TYPE_PING = "pingMessage";
    /**
     * im消息类型 文本消息
     */
    public static final String IM_MESS_TYPE_CHAT = "chatMessage";

    public static final String IM_ONLINE_MEMBERS_KEY = "online_members_key";
    public static final String IM_GROUP_HISTORY_MSG_KEY = "group_histroy_msg_key";

    /**
     * 默认群聊id
     */
    public static final Long IM_GROUP_ID = 999L;
    /**
     * 默认用户群聊id
     */
    public static final Long IM_DEFAULT_USER_ID = 999L;
    /**
     *
     */
    public static final String IM_GROUP_NAME = "hcw-group-study";
    /**
     * 群聊信息
     */
    public static Map<String,Object> IM_GROUP_MAP ;
    /**
     * 在线状态
     */
    public static final String ONLINE_STATUS = "online";
    /**
     * 隐身状态
     */
    public static final String HIDE_STATUS = "hide";
    /**
     * 历史消息的长度
     */
    public static final long GROUP_HISTORY_MSG_SIZE = 100L;

    /**
     * 合法数据检验
     */
    public static final String  CONCLUSION_TYPE= "conclusionType";
    /**
     * 合法数据检验
     */
    public static final String  BAIDU_TOKEN= "baidu:token";
}
