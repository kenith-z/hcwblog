package xyz.hcworld.hcwblog.vo;

import lombok.Data;
import xyz.hcworld.hcwblog.entity.Post;

/**
 * @ClassName: PostVo
 * @Author: 张红尘
 * @Date: 2020/7/12 16:52
 * @Version： 1.0
 */
@Data
public class PostVo extends Post {
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

    /**
     * 文章名称
     */
    private String categoryName;
}
