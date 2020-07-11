package xyz.hcworld.hcwblog.service.impl;

import xyz.hcworld.hcwblog.entity.UserMessage;
import xyz.hcworld.hcwblog.mapper.UserMessageMapper;
import xyz.hcworld.hcwblog.service.UserMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
