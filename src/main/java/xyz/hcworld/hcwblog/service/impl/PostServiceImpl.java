package xyz.hcworld.hcwblog.service.impl;

import xyz.hcworld.hcwblog.entity.Post;
import xyz.hcworld.hcwblog.mapper.PostMapper;
import xyz.hcworld.hcwblog.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
