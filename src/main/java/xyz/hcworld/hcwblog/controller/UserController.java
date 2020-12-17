package xyz.hcworld.hcwblog.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.hcworld.hcwblog.commont.lang.Result;
import xyz.hcworld.hcwblog.entity.Post;
import xyz.hcworld.hcwblog.entity.User;
import xyz.hcworld.hcwblog.entity.UserMessage;
import xyz.hcworld.hcwblog.shiro.AccountProfile;
import xyz.hcworld.hcwblog.vo.CommentVo;

import java.util.List;
import java.util.Map;

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
     * @param temp 新的用户信息
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
        setProfile((AccountProfile) accountProfile);
        SecurityUtils.getSubject().getSession().setAttribute("profile",accountProfile);
        return Result.success().action("/user/set#info");
    }

    /**
     * 修改用户头像
     *
     * @param file 头像文件
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
        setProfile(accountProfile);
        SecurityUtils.getSubject().getSession().setAttribute("profile",accountProfile);
        return Result.success(accountProfile.getAvatar());
    }

    /**
     * 修改密码
     *
     * @param nowpass 原密码
     * @param pass    新密码
     * @param repass  重复密码
     * @return
     */
    @ResponseBody
    @PostMapping("/repass")
    public Result repass(String nowpass, String pass, String repass) {
        if (StrUtil.hasBlank(nowpass, pass, repass)) {
            return Result.fail("密码不能为空");
        }
        if (!pass.equals(repass)) {
            return Result.fail("两次密码不相同");
        }
        return userService.updataUserPassword(getProfileId(), nowpass, pass).action("/user/set#pass");
    }

    /**
     * 获取用户收藏的文章
     *
     * @return 分页数据
     */
    @ResponseBody
    @GetMapping("/collection")
    public Result collection() {
        IPage page = postService.page(getPage(), new QueryWrapper<Post>()
                .inSql("id", "SELECT post_id FROM m_user_collection WHERE user_id=" + getProfileId())
        );
        return Result.success(page);
    }

    /**
     * 获取用户自己所发表文章
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/getUserPost")
    public Result getUserPost() {
        IPage page = postService.page(getPage(), new QueryWrapper<Post>()
                .eq("user_id", getProfileId())
                .orderByDesc("created"));

        return Result.success(page);
    }

    /**
     * 用户中心
     *
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return "/user/index";
    }

    /**
     * 我的消息
     *
     * @return
     */
    @GetMapping("/message")
    public String message() {

        IPage paging = messageService.paging(getPage(), new QueryWrapper<UserMessage>()
                .eq("to_user_id", getProfileId())
                .orderByAsc("created")
        );
        req.setAttribute("pageData", paging);
        return "/user/message";
    }

    /**
     * 清除消息
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/message/remove")
    public Result messageRemove(Long id,
                                @RequestParam(defaultValue = "false") Boolean all) {
        boolean remove = messageService.remove(new QueryWrapper<UserMessage>()
                .eq("to_user_id", getProfileId())
                .eq(!all, "id", id)
        );

        return remove ? Result.success() : Result.fail("删除失败");
    }
    @ResponseBody
    @PostMapping("/message/nums")
    public Map msgNums(){
       int count =  messageService.count(new QueryWrapper<UserMessage>()
                .eq("to_user_id",getProfileId())
               .eq("status","0")
       );
        return MapUtil.builder("status",0).put("count",count).build();
    }

}
