package xyz.hcworld.hcwblog.service;

import xyz.hcworld.hcwblog.commont.lang.Result;
import xyz.hcworld.hcwblog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.hcworld.hcwblog.shiro.AccountProfile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kenith-Zhang
 * @since 2020-07-12
 */
public interface UserService extends IService<User> {
    /**
     * 注册
     * @param user 用户实体
     * @return
     */
    Result register(User user);


    /**
     * 修改用户信息
     * @param user 用户信息
     * @return
     */
    Object updateUserInfo(AccountProfile profile,User user);

    /**
     * 登录
     * @param username 用户名
     * @param password 加密后的密码串
     * @return
     */
    AccountProfile login(String username, String password);
}
