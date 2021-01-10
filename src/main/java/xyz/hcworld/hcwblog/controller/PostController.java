package xyz.hcworld.hcwblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.hcworld.hcwblog.commont.lang.Result;
import xyz.hcworld.hcwblog.entity.Post;
import xyz.hcworld.hcwblog.vo.CommentVo;
import xyz.hcworld.hcwblog.vo.PostVo;


/**
 * @ClassName: PostController
 * @Author: 张红尘
 * @Date: 2020/7/11 17:37
 * @Version： 1.0
 */
@Controller
public class PostController extends BaseController {
    /**
     * 添加收藏
     */
    private static final String ADD = "add";
    /**
     * 取消收藏
     */
    private static final String REMOVE = "remove";
    /**
     * 文章列表
     *
     * @return 博客分类的ftlh文件
     */
    @GetMapping("category/{id:\\d*}")
    public String category(@PathVariable(name = "id") Long id) {
        //页数
        int pn = ServletRequestUtils.getIntParameter(req, "pn", 1);
        req.setAttribute("currentCategoryId", id);
        req.setAttribute("pn", pn);
        return "post/category";
    }

    /**
     * 查询文章详情
     *
     * @return
     */
    @GetMapping("post/{id:\\d*}")
    public String detail(@PathVariable(name = "id") Long id) {
        PostVo vo = postService.selectOnePost(new QueryWrapper<Post>().eq("p.id", id));
        Assert.notNull(vo, "文章已被删除");

        postService.setViewCount(vo);

        //评论 1分页，2文章id，3用户id，排序
        IPage<CommentVo> results = commentService.paing(getPage(), vo.getId(), null, "created");

        req.setAttribute("currentCategoryId", vo.getCategoryId());
        req.setAttribute("post", vo);
        req.setAttribute("pageData", results);
        return "post/detail";
    }

    /**
     * 编辑文章
     *
     * @return
     */
    @GetMapping("/post/edit")
    public String editPost(Long id) {
        System.out.println(id);
        if (id!=null){
            Assert.isTrue(id>0L,"该帖子不存在");
            Post post =postService.getById(id);
            Assert.isTrue(post!=null,"该帖子不存在");
            Assert.isTrue(getProfileId().equals(post.getUserId()),"没有权限修改此文章");
            req.setAttribute("post",post);
        }
        //查询帖子能选择的类型
        req.setAttribute("categories",categoryService.list());
        return "/post/edit";
    }



    /**
     * 判断用户是否收藏文章
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/collection/find")
    public Result collectionFind(Long pid) {

        return userCollectionService.collectionExistence(pid,getProfileId());
    }
    /**
     * 收藏文章
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/collection/{type}")
    public Result collectionAdd(@PathVariable String type, Long pid) {
        if (ADD.equals(type)){
           return userCollectionService.addCollection(pid,getProfileId());
        }else if (REMOVE.equals(type)){
            return userCollectionService.removeCollection(pid,getProfileId());
        }
        return Result.fail("异常请求").action("/post/"+pid);
    }



}
