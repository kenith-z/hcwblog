package xyz.hcworld.hcwblog.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.hcworld.hcwblog.commont.lang.Result;
import xyz.hcworld.hcwblog.config.RabbitConfig;
import xyz.hcworld.hcwblog.entity.Post;
import xyz.hcworld.hcwblog.search.mq.PostMqIndexMessage;
import xyz.hcworld.hcwblog.util.ConstantUtil;
import xyz.hcworld.hcwblog.vo.PostVo;

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
     *
     * @param id    帖子id
     * @param rank  0取消，1添加操作
     * @param field 要执行的操作
     * @return
     */
    @ResponseBody
    @PostMapping("/top")
    public Result postTop(Long id, Integer rank, String field) {
        if (id != null && id < 1) {
            return Result.fail("帖子不存在");
        }
        Post post = postService.getById(id);
        Assert.notNull(post, "该帖子已被删除");
        //删除,加精，置顶
        if (ConstantUtil.REMOVE.equals(field)) {
            postService.removeById(id);
            //删除相关消息以及收藏，评论
            messageService.removeByMap(MapUtil.of("post_id",id));
            userCollectionService.removeByMap(MapUtil.of("post_id",id));
            commentService.removeByMap(MapUtil.of("post_id",id));
            amqpTemplate.convertAndSend(RabbitConfig.ES_EXCHANGE,RabbitConfig.ES_BIND_KEY,new PostMqIndexMessage(post.getId(),ConstantUtil.REMOVE));
            String hot = "rank:post:" +id;
            //移除热榜
            if(redisUtil.hasKey(hot)){
                String key = "day:rank:" + DateUtil.format(post.getCreated(), DatePattern.PURE_DATE_FORMAT);
                //删除负责热榜统计的有序列表zSet
                redisUtil.zRem("week:rank",id);
                //删除
                redisUtil.zRem(key,id);
                //删除缓存的文章信息
                redisUtil.del(hot);
            }
            return Result.success();
        } else if (ConstantUtil.STATUS.equals(field)) {
            post.setRecommend(rank.equals(1));
        } else if (ConstantUtil.STICK.equals(field)) {
            post.setLevel(rank);
        }
        postService.updateById(post);

        return Result.success();
    }

    /**
     * 同步es数据
     * @return
     */
    @ResponseBody
    @PostMapping("/initEsData")
    public Result initEsData() {
        int size = 10000;
        Page page = new Page();
        Long total = 0L;
        page.setSize(size);
        for (int i = 1; i < size; i++) {
            page.setCurrent(i);
            IPage<PostVo> paging = postService.paging(page, null, null, null, null, null);
            int num = searchService.initEsData(paging.getRecords());
            total+=num;
            //当一页查不出10000条，说明这是最后一页
            if (paging.getRecords().size() < size) {
                break;
            }
        }

        return Result.success("ES索引初始化成功，共 "+total+" 条记录",null);
    }


}
