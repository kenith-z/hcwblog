package xyz.hcworld.hcwblog.service;

/**
 * WebSocket服务类接口
 * @ClassName: WssService
 * @Author: 张红尘
 * @Date: 2021-01-20
 * @Version： 1.0
 */
public interface WssService {
    /**
     *  通知新消息
     * @param toUserId 要通知的用户
     * @param count 新消息条数
     */
    void sendMessageCountToUser(Long toUserId,int count);
}
