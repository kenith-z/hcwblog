package xyz.hcworld.hcwblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyz.hcworld.hcwblog.entity.UserMessage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kenith-Zhang
 * @since 2020-07-12
 */
public interface UserMessageService extends IService<UserMessage> {
    /**
     * 自定义分页
     * @param page 页数
     * @param userMessageQueryWrapper 动态sql
     */
    IPage paging(Page page, QueryWrapper<UserMessage> userMessageQueryWrapper);
}
