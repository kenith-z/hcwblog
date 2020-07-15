package xyz.hcworld.hcwblog.vo;

import lombok.Data;
import xyz.hcworld.hcwblog.entity.Comment;

/**
 * 评论的Vo
 * @ClassName: CommentVo
 * @Author: 张红尘
 * @Date: 2020/7/15 0:13
 * @Version： 1.0
 */
@Data
public class CommentVo  extends Comment {
    /**
     * 用户id
     */
    private Long authorId;
    /**
     * 用户昵称
     */
    private String authorName;
    /**
     * 用户头像
     */
    private String authorAvatar;
}
