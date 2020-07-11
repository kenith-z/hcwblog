package xyz.hcworld.hcwblog.service.impl;

import xyz.hcworld.hcwblog.entity.User;
import xyz.hcworld.hcwblog.mapper.UserMapper;
import xyz.hcworld.hcwblog.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
