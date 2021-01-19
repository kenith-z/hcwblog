package xyz.hcworld.hcwblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import xyz.hcworld.hcwblog.entity.UserMessage;
import xyz.hcworld.hcwblog.mapper.UserMessageMapper;
import xyz.hcworld.hcwblog.service.UserMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.hcworld.hcwblog.vo.UserMessageVo;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Kenith-Zhang
 * @since 2020-07-12
 */
@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage> implements UserMessageService {
    @Autowired
    UserMessageMapper messageMapper;

    @Override
    public IPage<UserMessageVo> paging(Page page, QueryWrapper<UserMessage> userMessageQueryWrapper) {
        return messageMapper.selectMessages(page,userMessageQueryWrapper);
    }

    /**
     * 批量修改信息已读情况
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateToReaded(List<Long> ids) {
        if (ids.isEmpty()){
            return;
        }
        messageMapper.updateToReaded(ids.size(),new QueryWrapper<UserMessage>()
                .in("id",ids)
        );
    }
}
