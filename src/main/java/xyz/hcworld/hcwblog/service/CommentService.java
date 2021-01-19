package xyz.hcworld.hcwblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.hcworld.hcwblog.entity.Comment;
import xyz.hcworld.hcwblog.vo.CommentVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kenith-Zhang
 * @since 2020-07-12
 */
public interface CommentService extends IService<Comment> {
    /**
     * 获取文章评论
     * @param page 分页信息
     * @param postId 文章id
     * @param userId 用户id
     * @param order 排序信息
     * @return
     */
    IPage<CommentVo> paing(Page page, Long postId, Long userId, String order);

    /**
     * 查询用户自己的评论
     * @return
     */
    List<CommentVo> ownComments(Long userId);

    /**
     * 保存评论
     * @param userId 当前登录的用户
     * @param pid 文章id
     * @param content 评论内容
     * @return
     */
    boolean saveComments(Long userId,Long pid,String content);

    /**
     * 删除评论
     * @param cid 评论id
     * @param userId 当前登录的用户
     * @return
     */
    boolean deleteComments(Long cid, Long userId);

}
