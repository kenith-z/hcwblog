package xyz.hcworld.hcwblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import xyz.hcworld.hcwblog.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.hcworld.hcwblog.vo.PostVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Kenith-Zhang
 * @since 2020-07-12
 */
@Component
public interface PostMapper extends BaseMapper<Post> {
    /**
     * 查询分页的博客列表
     * @param page 分页信息
     * @param warpper 条件
     * @return
     */
    IPage<PostVo> selectPosts(Page page,@Param(Constants.WRAPPER) QueryWrapper warpper);

    /**
     * 查询文章详情
     * @param wrapper
     * @return
     */
    PostVo selectOnePost(@Param(Constants.WRAPPER)QueryWrapper<Post> wrapper);

    /**
     * 查询文章id和作者id
     * @param wrapper
     * @return
     */
    PostVo selectOnePostExistence(@Param(Constants.WRAPPER)QueryWrapper<Post> wrapper);

}
