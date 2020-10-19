package xyz.hcworld.hcwblog.schedules;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xyz.hcworld.hcwblog.entity.Post;
import xyz.hcworld.hcwblog.service.PostService;
import xyz.hcworld.hcwblog.util.QiniuUtil;
import xyz.hcworld.hcwblog.util.RedisUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;



/**
 * 定时器-同步文章阅读数
 *
 * @ClassName: ViewCountAsyTask
 * @Author: 张红尘
 * @Date: 2020/9/4 16:09
 * @Version： 1.0
 */
@Component
public class ViewCountSyncTask {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    PostService postService;

    @Autowired
    QiniuUtil qiniuUtil;
    /**
     * 每分钟同步
     */
    @Scheduled(cron = "0 0/1 * * * * ")
    public void task() {
        //获取redis缓存的文章信息
        Set<String> keys = redisTemplate.keys("rank:post:*");
        //如果没有文章信息就返回
        if (keys == null) {
            return;
        }
        int size = keys.size() << 1;
        List<String> ids = new ArrayList<>(size);
        for (String key : keys) {
            //如果有viewCount代表有人阅读了需要同步到mysql
            if (redisUtil.hHasKey(key, "post:viewCount")) {
                //截取字符串留下id
                ids.add(key.substring("rank:post:".length()));
            }
        }
        //如果没有更改就返回
        if (ids.isEmpty()) {
            return;
        }
        //需要更新阅读量的文章
        List<Post> posts = postService.list(new QueryWrapper<Post>().in("id", ids));

        posts.stream().forEach((post) -> {
            Integer viewCount = (Integer) redisUtil.hget("rank:post:" + post.getId(), "post:viewCount");
            post.setViewCount(viewCount);
        });
        //如果文章列表为空就结束
        if (posts.isEmpty()) {
            return;
        }
        if (postService.updateBatchById(posts)) {
            ids.stream().forEach(id -> {
                redisUtil.hdel("rank:post:" + id, "post:viewCount");
                System.out.println("------------>同步成功-"+id+"<------------");
            });
        }
    }


    /**
     * 每58分钟更新一次七牛云上传token
     */
    @Scheduled(cron = "0 0/30 * * * * ")
    public void taskToken() {
        Auth auth = qiniuUtil.getAuth();
        String token = auth.uploadToken(qiniuUtil.getConfig().getQiniuBucketName());
        qiniuUtil.setToken(token);
    }



}
