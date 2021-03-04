package xyz.hcworld.hcwblog.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;
import xyz.hcworld.hcwblog.commont.lang.Result;
import xyz.hcworld.hcwblog.config.RabbitConfig;
import xyz.hcworld.hcwblog.entity.Post;
import xyz.hcworld.hcwblog.search.mq.PostMqIndexMessage;
import xyz.hcworld.hcwblog.util.ConstantUtil;
import xyz.hcworld.hcwblog.vo.CommentVo;
import xyz.hcworld.hcwblog.vo.PostVo;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @ClassName: PostController
 * @Author: 张红尘
 * @Date: 2020/7/11 17:37
 * @Version： 1.0
 */
@Controller
public class PostController extends BaseController {
    private static final String PATTERN = "http(s)+://[^\\s]*(.jpg|.png|.gif|.bmp|.jpeg|.webp)";

    /**
     * 文章列表
     * @param id 文章id
     * @return
     */
    @GetMapping("category/{id:\\d*}")
    public String category(@PathVariable(name = "id") Long id) {
        //页数
        int pn = ServletRequestUtils.getIntParameter(req, "pn", 1);
        req.setAttribute("currentCategoryId", id);
        req.setAttribute("recommend", false);
        req.setAttribute("pn", pn);
        return "post/category";
    }
    /**
     * 精华文章列表
     * @param id 文章id
     * @return
     */
    @GetMapping("category/recommend/{id:\\d*}")
    public String recommend(@PathVariable(name = "id") Long id) {
        //页数
        int pn = ServletRequestUtils.getIntParameter(req, "pn", 1);
        req.setAttribute("currentCategoryId", id);
        req.setAttribute("recommend", true);
        req.setAttribute("pn", pn);
        return "post/category";
    }

    /**
     * 查询文章详情
     * @param id 文章id
     * @return
     */
    @GetMapping("/post/{id:\\d*}")
    public String detail(@PathVariable(name = "id") Long id) {
        PostVo vo = postService.selectOnePost(new QueryWrapper<Post>().eq("p.id", id));
        Assert.notNull(vo, "文章已被删除");

        postService.setViewCount(vo);

        //评论 1分页，2文章id，3用户id，排序
        IPage<CommentVo> results = commentService.paing(getPage(), vo.getId(), null, "created");
        //是否是帖子主人
        boolean master = getProfile()!=null&&vo.getAuthorId().equals(getProfileId());
        req.setAttribute("master",master);
        req.setAttribute("currentCategoryId", vo.getCategoryId());
        req.setAttribute("post", vo);
        req.setAttribute("pageData", results);
        return "post/detail";
    }

    /**
     * 编辑文章页
     *
     * @return
     */
    @GetMapping("/post/edit")
    public String editPost(Long id) {
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
     * 文章编辑
     * @param post 文章信息
     * @param vercode 验证码
     * @return
     */
    @ResponseBody
    @PostMapping("/post/submit")
    public Result submit(Post post, String vercode){
        String objectIsNull = currencyService.checkObjectIsNull(post);
        if (objectIsNull!=null) {
            //异常信息
            return Result.fail(objectIsNull);
        }
        // 验证码判空以及判断是否一致
        if (currencyService.checkVercode(req,vercode)) {
            return Result.fail("验证码不正确");
        }
        Assert.isTrue(post.getTitle().length()<=50,"标题过长");
        post.setUserId(getProfileId());
        post.setModified(new Date());
        //过滤html标签
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));
        List<String> list = new ArrayList<>();
        Pattern r = Pattern.compile(PATTERN);
        Matcher m = r.matcher(post.getContent());
        while (m.find()) {
            list.add(m.group());
        }
        int size =5;
        if (list.size()>size){
            return Result.fail("最多加入5张图片");
        }
        Set<String> h = new HashSet<>(list);
        list.clear();
        list.addAll(h);
        //修改文章
        if (post.getId()!=null){
            Post tempPost = postService.getById(post.getId());
            Assert.isTrue(getProfileId().equals(tempPost.getUserId()),"无权限修改此文章");
            if (baiduCensorUtil.textCensor(post.getTitle()+post.getContent())){
                return Result.fail("文章标题或内容有敏感内容！");
            }
            if (baiduCensorUtil.imagesCensor(list)){
                return Result.fail("图片有敏感内容！");
            }
            postService.updateById(post);
            long expireTime = (7 - DateUtil.between(new Date(), tempPost.getCreated(), DateUnit.DAY)) * 86400;
            redisUtil.hset("rank:post:"+post.getId(), "post:title", post.getTitle(), expireTime);
            amqpTemplate.convertAndSend(RabbitConfig.ES_EXCHANGE,RabbitConfig.ES_BIND_KEY,new PostMqIndexMessage(post.getId(),ConstantUtil.CREATE_OR_UPDATE));
            return Result.success().action("/post/"+post.getId());
        }
        //新文章
        post.setCreated(new Date());
        if (baiduCensorUtil.textCensor(post.getTitle()+post.getContent())){
            return Result.fail("文章标题或内容有敏感内容！");
        }
        postService.save(post);
        post = postService.getById(post.getId());
        postService.upWeekRank("day:rank:" + DateUtil.format(post.getCreated(), DatePattern.PURE_DATE_FORMAT),post);
        //通知消息给mq，告知更新或添加
        amqpTemplate.convertAndSend(RabbitConfig.ES_EXCHANGE,RabbitConfig.ES_BIND_KEY,new PostMqIndexMessage(post.getId(),ConstantUtil.CREATE_OR_UPDATE));
        return Result.success().action("/post/"+post.getId());
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/post/delete")
    public  Result delete(Long id){
        if(id!=null&&id<1){
            return Result.fail("帖子不存在");
        }
        Post post = postService.getById(id);
        Assert.notNull(post,"帖子已被删除");
        Assert.isTrue(post.getUserId().equals(getProfileId()),"无权限删除此文章");
        //删除帖子
        postService.removePost(id,post);
        return Result.success("删除成功",null,"/user/index");
    }

    /**
     * 评论
     * @param pid
     * @return
     */
    @ResponseBody
    @PostMapping("/post/reply")
    public  Result reply(Long pid,String content){
        Assert.notNull(pid,"找不到对应的文章");
        Assert.hasLength(content,"评论内容不能为空");
        if (baiduCensorUtil.textCensor(content)){
            return Result.fail("评论内容有敏感内容！");
        }
        commentService.saveComments(getProfileId(),pid,HtmlUtils.htmlEscape(content));
        return Result.success("评论成功",null,"/post/"+pid);
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    @ResponseBody
    @Transactional(rollbackFor = RuntimeException.class)
    @PostMapping("/comment/delete")
    public Result reply(Long id) {

        Assert.notNull(id, "评论id不能为空！");
        boolean deleteComments =  commentService.deleteComments(id,getProfileId());
        return Result.success(deleteComments);
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
     * 收藏及取消文章
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/collection/{type}")
    public Result collectionAdd(@PathVariable String type, Long pid) {
        if (ConstantUtil.ADD.equals(type)){
           return userCollectionService.addCollection(pid,getProfileId());
        }else if (ConstantUtil.REMOVE.equals(type)){
            return userCollectionService.removeCollection(pid,getProfileId());
        }
        return Result.fail("异常请求").action("/post/"+pid);
    }



}
