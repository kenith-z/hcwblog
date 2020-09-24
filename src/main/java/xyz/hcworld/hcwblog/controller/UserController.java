package xyz.hcworld.hcwblog.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.hcworld.hcwblog.entity.Post;
import xyz.hcworld.hcwblog.entity.User;
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
@Controller
public class UserController extends BaseController {

    @GetMapping("/user/home")
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
        req.setAttribute("user",user);
        req.setAttribute("posts",userPosts);
        req.setAttribute("comments",userComment);
        return "/user/home";
    }
}
