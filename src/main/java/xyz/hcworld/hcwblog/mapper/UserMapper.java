package xyz.hcworld.hcwblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import xyz.hcworld.hcwblog.entity.User;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Kenith-Zhang
 * @since 2020-07-12
 */
@Component
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询注册用户的email和username是否存在
     * @param wrapper
     * @return
     */
    Integer selectUserExistence(@Param(Constants.WRAPPER)QueryWrapper<User> wrapper);

}
