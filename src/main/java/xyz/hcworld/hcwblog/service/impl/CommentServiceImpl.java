package xyz.hcworld.hcwblog.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import xyz.hcworld.hcwblog.entity.Comment;
import xyz.hcworld.hcwblog.entity.Post;
import xyz.hcworld.hcwblog.entity.User;
import xyz.hcworld.hcwblog.entity.UserMessage;
import xyz.hcworld.hcwblog.mapper.CommentMapper;
import xyz.hcworld.hcwblog.mapper.CurrencyMapper;
import xyz.hcworld.hcwblog.service.*;
import xyz.hcworld.hcwblog.util.RedisUtil;
import xyz.hcworld.hcwblog.vo.CommentVo;

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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    PostService postService;
    @Autowired
    UserMessageService messageService;
    @Autowired
    UserService userService;
    @Autowired
    CurrencyMapper currencyMapper;
    @Autowired
    WssService wssService;
    @Autowired
    RedisUtil redisUtil;

    private final static String FLAG_ONE = "@";
    private final static String FLAG_TWO = " ";
    private final static long WEEK = 7L;

    /**
     * 获取文章评论
     *
     * @param page   分页信息
     * @param postId 文章id
     * @param userId 用户id
     * @param order  排序信息
     * @return
     */
    @Override
    public IPage<CommentVo> paing(Page page, Long postId, Long userId, String order) {
        return commentMapper.selectComments(page, new QueryWrapper<Comment>()
                .eq(postId != null, "post_id", postId)
                .eq(userId != null, "user_id", userId)
                .orderByDesc(order != null, order)
        );
    }

    /**
     * 获取30天内的评论
     *
     * @param userId 用户id
     * @return 评论列表
     */
    @Override
    public List<CommentVo> ownComments(Long userId) {
        return commentMapper.selectOwnComments(new QueryWrapper<Comment>()
                .eq("c.user_id", userId)
                .gt("c.created", DateUtil.lastMonth())
                .orderByDesc("c.created"));
    }

    /**
     * 发表评论
     *
     * @param userId  发表评论的用户id
     * @param pid     文章id
     * @param content 评论内容
     * @return true评论成功，false事务回滚
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean saveComments(Long userId, Long pid, String content) {
        Post post = postService.getById(pid);
        Assert.isTrue(post != null, "文章已被刪除");
        Comment comment = new Comment(content, null, post.getId(), userId, null, null, null);
        comment.setCreated(new Date());
        comment.setModified(new Date());
        post.setCommentCount(post.getCommentCount() + 1);
        this.save(comment);
        //评论数加一
        postService.updateById(post);
        if(DateUtil.between(post.getCreated(), new Date(), DateUnit.DAY)<WEEK) {
            //本周热议数量加一
            postService.incrCommentCountAndUnionForWeekRank(post.getId(), true);
        }
        //除去作者自己评论自己，其他都要通知作者有人评论文章,
        if (!post.getUserId().equals(userId)) {
            tips(userId, post.getUserId(), post.getId(), comment.getId(), content, 1);
        }

        //通知被@的人，有人回复评论
        if (content.startsWith(FLAG_ONE)) {
            String username = content.substring(1, content.indexOf(FLAG_TWO));
            User user = userService.getOne(new QueryWrapper<User>()
                    .eq("username", username)
            );
            if (user != null) {
                tips(userId, user.getId(), post.getId(), comment.getId(), content, 2);
            }
        }
        return true;
    }

    /**
     * 删除评论
     *
     * @param userId  发表评论的用户id
     * @param cid     评论id
     * @return true评论成功
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean deleteComments(Long cid, Long userId) {
        Comment comment = this.getById(cid);
        Assert.notNull(comment, "找不到对应评论！");
        Assert.isTrue(comment.getUserId().equals(userId)||userId==1, "不是你发表的评论！");
        this.removeById(cid);
        // 评论数量减一
        Post post = postService.getById(comment.getPostId());
        post.setCommentCount(post.getCommentCount() - 1);
        postService.saveOrUpdate(post);
        messageService.remove(new QueryWrapper<UserMessage>().eq("comment_id",cid));

        if(DateUtil.between(post.getCreated(), new Date(), DateUnit.DAY)<WEEK){
            //评论数量减一
            postService.incrCommentCountAndUnionForWeekRank(comment.getPostId(), false);
        }
        return true;
    }



    /**
     * 通知封装
     * @param formUserId 发送人
     * @param toUserId 接收人
     * @param postId 文章id
     * @param commentId
     * @param content
     * @param type
     */
    private void tips(Long formUserId,Long toUserId,Long postId,Long commentId,String content,int type){
        UserMessage userMessage = new UserMessage(formUserId, toUserId, postId, commentId, content, type, 0);
        userMessage.setCreated(new Date());
        userMessage.setModified(new Date());
        messageService.save(userMessage);
        int count = messageService.count(new QueryWrapper<UserMessage>()
                .eq("to_user_id", toUserId)
                .eq("status", "0")
        );
        //即时通知(websocket)
        wssService.sendMessageCountToUser(toUserId,count);
    }


}
