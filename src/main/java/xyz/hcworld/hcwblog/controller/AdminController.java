package xyz.hcworld.hcwblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.hcworld.hcwblog.commont.lang.Result;
import xyz.hcworld.hcwblog.entity.Post;
import xyz.hcworld.hcwblog.util.ConstantUtil;

/**
 * @ClassName: AdminController
 * @Author: 张红尘
 * @Date: 2021-01-15
 * @Version： 1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    /**
     * 管理员对帖子进行删除，置顶，加精及取消
     * @param id 帖子id
     * @param rank 0取消，1添加操作
     * @param field 要执行的操作
     * @return
     */
    @ResponseBody
    @PostMapping("/top")
    public Result postTop(Long id,Integer rank,String field){
        if(id!=null&&id<1){
            return Result.fail("帖子不存在");
        }
        Post post = postService.getById(id);
        Assert.notNull(post,"该帖子已被删除");
        //删除,加精，置顶
        if (ConstantUtil.REMOVE.equals(field)){
            postService.removeById(id);
            return Result.success();
        }else if (ConstantUtil.STATUS.equals(field)){
            post.setRecommend(rank.equals(1));
        }else if (ConstantUtil.STICK.equals(field)){
            post.setLevel(rank);
        }
        postService.updateById(post);

        return  Result.success();
    }



}
