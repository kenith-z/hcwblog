package xyz.hcworld.hcwblog.service.impl;

import xyz.hcworld.hcwblog.entity.Comment;
import xyz.hcworld.hcwblog.mapper.CommentMapper;
import xyz.hcworld.hcwblog.service.CommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
