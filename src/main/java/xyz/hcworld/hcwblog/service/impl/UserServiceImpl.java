package xyz.hcworld.hcwblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.hcworld.hcwblog.commont.lang.Result;
import xyz.hcworld.hcwblog.entity.User;
import xyz.hcworld.hcwblog.mapper.UserMapper;
import xyz.hcworld.hcwblog.service.UserService;
import xyz.hcworld.hcwblog.shiro.AccountProfile;
import xyz.hcworld.hcwblog.util.KeyUtil;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Kenith-Zhang
 * @since 2020-07-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;




    @Override
    public Result register(User user) {
        // 条件构造器
        QueryWrapper wrapper = new QueryWrapper<User>()
                .eq("email", user.getEmail())
                .or()
                .eq("username", user.getUsername());
        Integer existence = userMapper.selectUserExistence(wrapper);
        if (existence != null) {
            return Result.fail("用户名或邮箱已被占用");
        }
        // 安全性设置，防止前端传恶意邮箱，用户名密码以外的属性
        User temp = new User();
        temp.setUsername(user.getUsername());
        temp.setEmail(user.getEmail());
        // 国密sm3摘要算法
        temp.setPassword(KeyUtil.encryption(user.getPassword()));
        temp.setCreated(new Date());
        temp.setAvatar("https://hcworld.xyz/images/topima.jpg");
        temp.setPoint(0);
        temp.setVipLevel(0);
        temp.setCommentCount(0);
        temp.setPostCount(0);

        return this.save(temp)? Result.success():Result.fail("请稍后再试");
    }

    @Override
    public AccountProfile login(String email, String password) {
        User user = this.getOne(new QueryWrapper<User>().eq("email",email));
        // 用户不存在
        if (user==null){
            throw new UnknownAccountException();
        }
        // 密码不正确
        if (!user.getPassword().equals(password)){
            throw new IncorrectCredentialsException();
        }
        // 修改最后登录时间
        user.setLasted(LocalDateTime.now());

        this.updateById(user);

        AccountProfile profile = new AccountProfile();
        // 复制属性
        BeanUtil.copyProperties(user,profile);

        return profile;
    }


}
