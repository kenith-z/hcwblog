package xyz.hcworld.hcwblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyz.hcworld.hcwblog.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.hcworld.hcwblog.vo.PostVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kenith-Zhang
 * @since 2020-07-12
 */
public interface PostService extends IService<Post> {
    /**
     * 博客的分页信息
     * @param page 分页信息
     * @param categoryId 分类
     * @param userId 用户信息
     * @param level 置顶
     * @param recommend 精选
     * @param order 排序
     * @return
     */
    IPage paging(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String order);

    /**
     * 获取文章
     * @param wrapper
     * @return
     */
    PostVo selectOnePost(QueryWrapper<Post> wrapper);

    /**
     * 初始化本周热议
     */
    void initWeekRank();
}
