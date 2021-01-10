package xyz.hcworld.hcwblog.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.hcworld.hcwblog.commont.lang.Result;
import xyz.hcworld.hcwblog.entity.Post;
import xyz.hcworld.hcwblog.entity.UserCollection;
import xyz.hcworld.hcwblog.mapper.CurrencyMapper;
import xyz.hcworld.hcwblog.mapper.PostMapper;
import xyz.hcworld.hcwblog.mapper.UserCollectionMapper;
import xyz.hcworld.hcwblog.service.UserCollectionService;

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
public class UserCollectionServiceImpl extends ServiceImpl<UserCollectionMapper, UserCollection> implements UserCollectionService {
    /**
     * 通用的mapper
     */
    @Autowired
    CurrencyMapper currencyMapper;
    /**
     * 文章的mapper
     */
    @Autowired
    PostMapper postMapper;

    @Override
    public Result addCollection(Long pid, Long userId) {
        Object obj= checkPost(pid);
        if (Result.class.equals(obj.getClass())){
            return (Result) obj;
        }
        if (checkCollection(pid,userId)){
            return Result.fail("重复收藏");
        }
        Post post = (Post)obj;
        UserCollection collection = new UserCollection();
        collection.setUserId(userId);
        collection.setPostId(pid);
        collection.setCreated(new Date());
        collection.setModified(new Date());
        collection.setPostUserId(post.getUserId());
        this.save(collection);
        return Result.success("add");
    }
    @Override
    public Result removeCollection(Long pid, Long userId) {
        this.remove(new QueryWrapper<UserCollection>()
                .eq("post_id", pid)
                .eq("user_id", userId));
        return Result.success("remove");
    }


    @Override
    public Result collectionExistence(Long pid, Long userId) {
        Object obj= checkPost(pid);
        if (Result.class.equals(obj.getClass())){
            return (Result) obj;
        }
        return Result.success(MapUtil.of("collection",checkCollection(pid,userId)));
    }



    /**
     * 检查文章是否存在
     *
     * @param pid
     * @return
     */
    private Object checkPost(Long pid) {
        Post post = postMapper.selectOnePostExistence(new QueryWrapper<Post>()
                .eq("id", pid));
        if (post == null) {
            return Result.fail("帖子不存在");
        }
        return post;
    }
    /**
     * 检查是否收藏帖子
     *
     * @param pid
     * @param userId
     * @return true:收藏 false：未收藏
     */
    private boolean checkCollection(Long pid, Long userId) {
        Integer existenceCollection = currencyMapper.selectExistence("m_user_collection", new QueryWrapper<>()
                .eq("post_id", pid)
                .eq("user_id", userId)
        );
        return existenceCollection!=null;
    }

}
