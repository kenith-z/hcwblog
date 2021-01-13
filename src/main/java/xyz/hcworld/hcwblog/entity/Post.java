package xyz.hcworld.hcwblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
@TableName("m_post")
public class Post extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;

    /**
     * 编辑模式：html可视化，markdown ..
     */
    private String editMode;
    @NotNull(message = "分类不能为空")
    private Long categoryId;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 支持人数
     */
    private Integer voteUp;

    /**
     * 反对人数
     */
    private Integer voteDown;

    /**
     * 访问量
     */
    private Integer viewCount;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 是否为精华
     */
    private Boolean recommend;

    /**
     * 置顶等级
     */
    private Integer level;


}
