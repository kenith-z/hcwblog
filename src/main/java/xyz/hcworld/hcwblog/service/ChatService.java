package xyz.hcworld.hcwblog.service;

import xyz.hcworld.hcwblog.im.vo.ImMess;
import xyz.hcworld.hcwblog.im.vo.ImUser;
import xyz.hcworld.hcwblog.shiro.AccountProfile;

import java.util.List;

/**
 *  im服务接口
 * @ClassName: ChatService
 * @Author: 张红尘
 * @Date: 2021-02-08
 * @Version： 1.0
 */
public interface ChatService {

    /**
     * 构建im的用户
     * @param profile
     * @return
     */
    ImUser getCurrentUser(AccountProfile profile);

    /**
     * 设置历史信息
     * @param imMess
     */
    void setGroupHistoryMsg(ImMess imMess);

    /**
     * 获取历史信息
     * @param count
     * @return
     */
    List<Object> getGroupHistoryMsg(int count);

}
