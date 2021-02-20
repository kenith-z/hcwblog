package xyz.hcworld.hcwblog.controller;

import cn.hutool.core.map.MapUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.hcworld.hcwblog.commont.lang.Result;
import xyz.hcworld.hcwblog.im.vo.ImUser;

import java.util.List;
import java.util.Map;

import static xyz.hcworld.hcwblog.util.ConstantUtil.IM_GROUP_MAP;

/**
 * @ClassName: ChatController
 * @Author: 张红尘
 * @Date: 2021-02-08
 * @Version： 1.0
 */
@RestController
@RequestMapping("/chat")
public class ChatController extends BaseController {


    @GetMapping("/getMineAndGroupData")
    public Result getMineAndGroupData() {
        /**
         * 获取群聊信息
         */
        Map<String, Object> group = IM_GROUP_MAP;

        ImUser imUser = chatService.getCurrentUser(getProfile());

        return Result.success(MapUtil.builder()
                .put("group", group)
                .put("mine", imUser)
                .map());
    }

    @GetMapping("/getGroupHistoryMsg")
    public Result getGroupHistoryMsg() {
        List<Object> messages = chatService.getGroupHistoryMsg(20);
        return Result.success(messages);
    }


}
