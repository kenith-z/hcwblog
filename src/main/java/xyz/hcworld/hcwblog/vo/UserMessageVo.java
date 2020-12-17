package xyz.hcworld.hcwblog.vo;

import lombok.Data;
import xyz.hcworld.hcwblog.entity.UserMessage;

/**
 * @ClassName: UserMessageVo
 * @Author: 张红尘
 * @Date: 2020/12/6 21:39
 * @Version： 1.0
 */
@Data
public class UserMessageVo extends UserMessage {
    private String toUserName;
    private String fromUserName;
    private String fromAvatar;
    private String postTitle;
    private String commentCreated;
}
