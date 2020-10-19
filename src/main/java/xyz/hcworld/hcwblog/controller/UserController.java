package xyz.hcworld.hcwblog.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.hcworld.hcwblog.commont.lang.Result;
import xyz.hcworld.hcwblog.entity.Post;
import xyz.hcworld.hcwblog.entity.User;
import xyz.hcworld.hcwblog.shiro.AccountProfile;
import xyz.hcworld.hcwblog.vo.CommentVo;

import java.util.List;

/**
 * 登录后的用户信息
 *
 * @ClassName: UserController
 * @Author: 张红尘
 * @Date: 2020/9/22 10:55
 * @Version： 1.0
 */
@Slf4j
@Controller
@RequestMapping("/user")
@Validated
public class UserController extends BaseController {


    /**
     * 个人主页
     *
     * @return
     */
    @GetMapping("/home")
    public String userHome() {
        User user = userService.getById(getProfileId());
        user.setPassword("");
        //获取30天内发表的文章
        List<Post> userPosts = postService.list(new QueryWrapper<Post>()
                .eq("user_id", getProfileId())
                //30天
                .gt("created", DateUtil.lastMonth())
                .orderByDesc("created")
        );
        //获取30天内的评论
        List<CommentVo> userComment = commentService.ownComments(getProfileId());
        req.setAttribute("user", user);
        req.setAttribute("posts", userPosts);
        req.setAttribute("comments", userComment);
        return "/user/home";
    }

    /**
     * 设置基本信息页
     *
     * @return
     */
    @GetMapping("/set")
    public String set() {
        User user = userService.getById(getProfileId());
        user.setPassword("");
        System.out.println(user.toString());
        req.setAttribute("user", user);
        return "/user/set";
    }

    /**
     * 用户修改信息
     *
     * @param temp
     * @return
     */
    @PostMapping("/set")
    @ResponseBody
    public Result doSet(User temp) {
        if (StrUtil.isBlank(temp.getUsername())) {
            return Result.fail("昵称不能为空");
        }
        Object accountProfile = userService.updateUserInfo(getProfile(), temp);
        if (Result.class == accountProfile.getClass()) {
            return (Result) accountProfile;
        }
        steProfile((AccountProfile) accountProfile);
        return Result.success().action("/user/set#info");
    }

    /**
     * 修改用户头像
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public Result uploadAvatar(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.fail("文件为空");
        }
        AccountProfile accountProfile = getProfile();
        //更换头像
        Object url = userService.updateUserAvatar(getProfileId(), accountProfile.getAvatar(), file);
        //如果有异常则返回Result类型对象
        if (Result.class == url.getClass()) {
            return (Result) url;
        }
        //刷新session
        accountProfile.setAvatar((String) url);
        steProfile(accountProfile);
        return Result.success(accountProfile.getAvatar());
    }

    @ResponseBody
    @PostMapping("/repass")
    public Result repass(String nowpass,String pass,String repass) {
        if (StrUtil.hasBlank(nowpass,pass,repass)){
            return Result.fail("密码不能为空");
        }
        if (!pass.equals(repass)) {
            return Result.fail("两次密码不相同");
        }
        return userService.updataUserPassword(getProfileId(), nowpass, pass).action("/user/set#pass");
    }


}
