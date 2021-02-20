package xyz.hcworld.hcwblog.im.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *
 * @ClassName: ImMess
 * @Author: 张红尘
 * @Date: 2021-02-09
 * @Version： 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImMess {
    /**
     * 消息id，私聊则是用户id，群聊则是群组id
     */
    private Long id;
    /**
     * 昵称
     */
    private String username;
    /**
     * 聊天窗口来源类型，从发送消息的to里获取
     */
    private String type;
    /**
     * 内容
     */
    private String content;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 是否我发送的消息
     */
    private Boolean mine;
    /**
     * 暂无用处
     */
    private Long cid;
    /**
     * 头像
     */
    private Long fromid;
    /**
     * 发送时间
     */
    private Date timestamp;



}
