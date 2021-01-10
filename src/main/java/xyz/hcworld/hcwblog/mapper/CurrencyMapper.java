package xyz.hcworld.hcwblog.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 *  通用sql接口
 * @ClassName: CurrencyMapper
 * @Author: 张红尘
 * @Date: 2020/12/24
 * @Version： 1.0
 */
@Component
public interface CurrencyMapper {

    /**
     * 查询数据是否存在
     * @param tableName
     * @param wrapper
     * @return
     */
    Integer selectExistence(String tableName,@Param(Constants.WRAPPER) QueryWrapper wrapper);

}
