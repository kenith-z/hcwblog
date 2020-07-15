package xyz.hcworld.hcwblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import xyz.hcworld.hcwblog.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.hcworld.hcwblog.vo.CommentVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Kenith-Zhang
 * @since 2020-07-12
 */
@Component
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 根据条件查询文章评论
     * @param page 分页信息
     * @param wrapper 条件
     * @return
     */
    IPage<CommentVo> selectComments(Page page,@Param(Constants.WRAPPER) QueryWrapper<Comment> wrapper);
}
