package xyz.hcworld.hcwblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.hcworld.hcwblog.commont.lang.Result;
import xyz.hcworld.hcwblog.entity.User;
import xyz.hcworld.hcwblog.mapper.UserMapper;
import xyz.hcworld.hcwblog.service.UserService;
import xyz.hcworld.hcwblog.shiro.AccountProfile;
import xyz.hcworld.hcwblog.util.KeyUtil;
import xyz.hcworld.hcwblog.util.QiniuUtil;
import xyz.hcworld.hcwblog.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
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

    @Autowired
    QiniuUtil qiniuUtil;


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
        temp.setAvatar("https://img.hcworld.xyz/code/duck/2020-10-16-ef9a466855094e51989791e6ad6a7be3.jpg");
        temp.setPoint(0);
        temp.setVipLevel(0);
        temp.setCommentCount(0);
        temp.setPostCount(0);

        return this.save(temp)? Result.success():Result.fail("请稍后再试");
    }

    @Override
    public Object updateUserInfo(AccountProfile profile,User user) {
        // 条件构造器
        QueryWrapper wrapper = new QueryWrapper<User>()
                .eq("username", user.getUsername())
                .ne("id",profile.getId());
        Integer existence = userMapper.selectUserExistence(wrapper);
        if (existence != null) {
            return Result.fail("昵称已被占用");
        }
        User temp = this.getById(profile.getId());
        temp.setUsername(user.getUsername());
        temp.setGender(user.getGender());
        temp.setSign(user.getSign());
        this.updateById(temp);

        //更新session 的缓存
        profile.setUsername(temp.getUsername());
        profile.setSign(temp.getSign());
        profile.setVipLevel(temp.getVipLevel());

        return profile;
    }

    @Override
    public Object updateUserAvatar(Long id,String avatar,MultipartFile file) {
        // 获取文件的名称
        String fileName = file.getOriginalFilename();
        if (StrUtil.isBlank(fileName)){
            return Result.fail("文件名为空");
        }
        // 使用工具类根据上传文件生成唯一图片名称
        String imgName = StringUtil.getRandomImgName(fileName);

        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            log.error("------------上传头像文件流异常------------");
            e.printStackTrace();
            return Result.fail("上传失败");
        }
        //执行上传
        String path = qiniuUtil.uploadQiNiuImg(inputStream, imgName);
        //删除现在的头像
        String domain = qiniuUtil.getDomain();
        if(avatar.length()>domain.length()) {
            qiniuUtil.deleteQiNiuImg(avatar.substring(domain.length() + 1));
        }
        //保存头像
        User temp = this.getById(id);
        temp.setAvatar(path);
        this.updateById(temp);
        return path;
    }

    @Override
    public Result updataUserPassword(Long id, String nowpass, String pass) {
        User user = this.getById(id);
        if (!user.getPassword().equals(KeyUtil.encryption(nowpass))){
            return Result.fail("密码不正确");
        }
        user.setPassword(KeyUtil.encryption(pass));
        this.updateById(user);

        return Result.success();
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
