package xyz.hcworld.hcwblog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import xyz.hcworld.hcwblog.service.WssService;

/**
 * @ClassName: WssServiceimpl
 * @Author: 张红尘
 * @Date: 2021-01-20
 * @Version： 1.0
 */
@Service
public class WssServiceImpl implements WssService {

    @Autowired
    SimpMessagingTemplate messagingTemplate;
    @Async
    @Override
    public void sendMessageCountToUser(Long toUserId,int count) {
        //发送WebSock通知 "/user/"+${profile.id}+"/messCount"
        messagingTemplate.convertAndSendToUser(toUserId.toString(),"/messCount",count);
    }
}
