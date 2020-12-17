package xyz.hcworld.hcwblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Kenith-Zhang
 * @since 2020-07-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("m_user_message")
public class UserMessage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 发送消息的用户ID
     */
    private Long fromUserId;

    /**
     * 接收消息的用户ID
     */
    private Long toUserId;

    /**
     * 消息可能关联的帖子
     */
    private Long postId;

    /**
     * 消息可能关联的评论
     */
    private Long commentId;
    /**
     * 内容
     */
    private String content;

    /**
     * 消息类型
     * 0系统消息，1评论文章，2评论评论
     */
    private Integer type;
    /**
     * 状态：已读，未读
     */
    private Integer status;

}
