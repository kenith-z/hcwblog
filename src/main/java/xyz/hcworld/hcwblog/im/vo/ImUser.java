package xyz.hcworld.hcwblog.im.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * im的用户信息
 * @ClassName: ImUser
 * @Author: 张红尘
 * @Date: 2021-02-08
 * @Version： 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImUser {
    /**
     * id
     */
    private Long id;
    /**
     * 昵称
     */
    private String username;
    /**
     * 在线状态
     */
    private String status;
    /**
     * 个性签名
     */
    private String sign;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 是否我发送的消息
     */
    private Boolean mine;
    /**
     * 消息内容
     */
    private String content;

}
