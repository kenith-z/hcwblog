package xyz.hcworld.hcwblog.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.hcworld.hcwblog.entity.Post;
import xyz.hcworld.hcwblog.mapper.PostMapper;
import xyz.hcworld.hcwblog.service.PostService;
import xyz.hcworld.hcwblog.service.UserCollectionService;
import xyz.hcworld.hcwblog.util.RedisUtil;
import xyz.hcworld.hcwblog.vo.PostVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Kenith-Zhang
 * @since 2020-07-12
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    /**
     * 文章的mapper
     */
    @Autowired
    PostMapper postMapper;
    /**
     * redis工具类
     */
    @Autowired
    RedisUtil redisUtil;
    /**
     * 收藏的服务类
     */
    @Autowired
    UserCollectionService collectionService;

    /**
     * 博客的分页信息
     *
     * @param page       分页信息
     * @param categoryId 分类
     * @param userId     用户信息
     * @param level      置顶
     * @param recommend  精选
     * @param order      排序
     * @return
     */
    @Override
    public IPage<PostVo> paging(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String order) {
        if (level == null) {
            level = -1;
        }
        //条件构造器
        QueryWrapper wrapper = new QueryWrapper<Post>()
                .eq(categoryId != null, "category_id", categoryId)
                .eq(userId != null, "user_id", userId)
                .eq(level == 0, "level", 0)
                .gt(level > 0, "level", 0)
                .orderByDesc(order != null, order);
        return postMapper.selectPosts(page, wrapper);
    }

    /**
     * 获取文章
     *
     * @param wrapper
     * @return
     */
    @Override
    public PostVo selectOnePost(QueryWrapper<Post> wrapper) {
        return postMapper.selectOnePost(wrapper);
    }

    /**
     * 初始化本周热议
     */
    @Override
    public void initWeekRank() {
        // 获取7天内发表的文章
        List<Post> posts = this.list(new QueryWrapper<Post>()
                // 上周
                .ge("created", DateUtil.lastWeek())
                .select("id," +
                        "  title," +
                        "  user_id," +
                        "  comment_count," +
                        "  view_count," +
                        "  created")
        );
        // 初始化文章的总阅读量
        for (Post post : posts) {
            String key = "day:rank:" + DateUtil.format(post.getCreated(), DatePattern.PURE_DATE_FORMAT);
            forWeekRank(key, post);
        }

        // 做并集
        this.zunionAndStoreLast7DayForWeekRank();

    }

    /**
     * 更新每周热议
     */
    @Override
    public void upWeekRank(String key, Post post) {
        forWeekRank(key, post);
        // 做并集
        this.zunionAndStoreLast7DayForWeekRank();
    }


    /**
     * 本周文章每日评论并集化
     */
    private void zunionAndStoreLast7DayForWeekRank() {
        //倒数
        int countDown = -6;
        //集合key的名字
        String key = "week:rank";
        //每天热议的key
        String destKey = "day:rank:" + DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT);
        List<String> otherKeys = new ArrayList<>(10);
        for (int i = countDown; i < 0; i++) {
            String temp = "day:rank:" + DateUtil.format(DateUtil.offsetDay(new Date(), i), DatePattern.PURE_DATE_FORMAT);
            otherKeys.add(temp);
        }
        //合并
        redisUtil.zUnionAndStore(destKey, otherKeys, key);
    }

    /**
     * 文章的总阅读量
     */
    private void forWeekRank(String key, Post post) {
        // 初始化文章的总阅读量
        redisUtil.zSet(key, post.getId(), post.getCommentCount());
        // 七天后自动过期(假设14号发表，7-（16-14）=5)
        long between = DateUtil.between(new Date(), post.getCreated(), DateUnit.DAY);
        // 有效时间
        long expireTime = (7 - between) * 86400;
        redisUtil.expire(key, expireTime);

        // 缓存文章的基本信息（id，标题，评论，作者）
        this.hashCachePostIdAndTitle(post, expireTime);

    }

    /**
     * 缓存文章基本信息
     *
     * @param post
     * @param expireTime
     */
    @Override
    public void hashCachePostIdAndTitle(Post post, long expireTime) {
        String key = "rank:post:" + post.getId();
        boolean hasKey = redisUtil.hasKey(key);
        if (!hasKey) {
            redisUtil.hset(key, "post:id", post.getId(), expireTime);
            redisUtil.hset(key, "post:title", post.getTitle(), expireTime);
            //可以用合并数计算
            redisUtil.hset(key, "post:commentCount", post.getCommentCount(), expireTime);
            redisUtil.hset(key, "post:viewCount", post.getViewCount(), expireTime);
        }
    }

    @Override
    public void incrCommentCountAndUnionForWeekRank(long postId, boolean isIncr) {
        String destKey = "day:rank:" + DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT);
        redisUtil.zIncrementScore(destKey, postId, isIncr ? 1 : -1);
        Post post = this.getById(postId);
        // 七天后自动过期(假设14号发表，7-（16-14）=5)
        long between = DateUtil.between(new Date(), post.getCreated(), DateUnit.DAY);
        // 有效时间
        long expireTime = (7 - between) * 86400;
        //缓存这篇文章的信息
        this.hashCachePostIdAndTitle(post, expireTime);
        //重新做并集
        this.zunionAndStoreLast7DayForWeekRank();
    }

    @Override
    public void setViewCount(PostVo vo) {
        String key = "rank:post:" + vo.getId();
        // 1.从缓存中获取viewcount
        Integer viewCount = (Integer) redisUtil.hget(key, "post:viewCount");
        // 2.如果没有就先从实体里获取再加一
        if (viewCount != null) {
            vo.setViewCount(viewCount + 1);
        } else {
            vo.setViewCount(vo.getViewCount() + 1);
        }
        // 3.同步到缓存
        redisUtil.hset(key, "post:viewCount", vo.getViewCount());
    }


}
