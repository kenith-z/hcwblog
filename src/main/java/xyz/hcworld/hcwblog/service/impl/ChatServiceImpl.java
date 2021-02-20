package xyz.hcworld.hcwblog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.hcworld.hcwblog.im.vo.ImMess;
import xyz.hcworld.hcwblog.im.vo.ImUser;
import xyz.hcworld.hcwblog.service.ChatService;
import xyz.hcworld.hcwblog.shiro.AccountProfile;
import xyz.hcworld.hcwblog.util.ConstantUtil;
import xyz.hcworld.hcwblog.util.RedisUtil;

import java.util.List;

import static xyz.hcworld.hcwblog.util.ConstantUtil.ONLINE_STATUS;

/**
 * 群聊服务
 *
 * @ClassName: ChatServiceImpl
 * @Author: 张红尘
 * @Date: 2021-02-08
 * @Version： 1.0
 */
@Slf4j
@Service("chatService")
public class ChatServiceImpl implements ChatService {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public ImUser getCurrentUser(AccountProfile profile) {
        return new ImUser(profile.getId(), profile.getUsername(), ONLINE_STATUS, null, profile.getAvatar(), null, null);
    }

    @Override
    public void setGroupHistoryMsg(ImMess imMess) {
        //存进list列表
        redisUtil.lSet(ConstantUtil.IM_GROUP_HISTORY_MSG_KEY, imMess, 24 * 60 * 60);
    }

    @Override
    public List<Object> getGroupHistoryMsg(int count) {
        //获取历史记录的长度
        long length = redisUtil.lGetListSize(ConstantUtil.IM_GROUP_HISTORY_MSG_KEY);
        //获取指定的长度
        return redisUtil.lGet(ConstantUtil.IM_GROUP_HISTORY_MSG_KEY, length - count < 0 ? 0 : length - count, length);
    }

}
