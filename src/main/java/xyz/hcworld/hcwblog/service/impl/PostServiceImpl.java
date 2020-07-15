package xyz.hcworld.hcwblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.hcworld.hcwblog.entity.Post;
import xyz.hcworld.hcwblog.mapper.PostMapper;
import xyz.hcworld.hcwblog.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.hcworld.hcwblog.vo.PostVo;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Kenith-Zhang
 * @since 2020-07-12
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
    @Autowired
    PostMapper postMapper;

    @Override
    public IPage<PostVo> paging(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String order) {
        if (level==null){
            level=-1;
        }
        //条件构造器
        QueryWrapper warpper = new QueryWrapper<Post>()
                .eq(categoryId!=null,"category_id",categoryId)
                .eq(userId!=null,"user_id",userId)
                .eq(level==0,"level",0)
                .gt(level>0,"level",0)
                .orderByDesc(order!=null,order);
        return postMapper.selectPosts(page,warpper);
    }

    @Override
    public PostVo selectOnePost(QueryWrapper<Post> wrapper) {
        return postMapper.selectOnePost(wrapper);
    }
}
