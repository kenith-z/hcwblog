package xyz.hcworld.hcwblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import xyz.hcworld.hcwblog.entity.UserMessage;
import xyz.hcworld.hcwblog.vo.UserMessageVo;

/**
 * <p>
 *  用户信息Mapper 接口
 * </p>
 *
 * @author Kenith-Zhang
 * @since 2020-07-12
 */
@Component
public interface UserMessageMapper extends BaseMapper<UserMessage> {
    /**
     *  查询用户信息
     * @param page 页码
     * @param userMessageQueryWrapper 条件
     * @return
     */
    IPage<UserMessageVo> selectMessages(Page page,@Param(Constants.WRAPPER) QueryWrapper<UserMessage> userMessageQueryWrapper);
}
