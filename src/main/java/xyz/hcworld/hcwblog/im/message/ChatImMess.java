package xyz.hcworld.hcwblog.im.message;

import lombok.Data;
import xyz.hcworld.hcwblog.im.vo.ImTo;
import xyz.hcworld.hcwblog.im.vo.ImUser;

/**
 * im的输入消息
 * @ClassName: ChatImMess
 * @Author: 张红尘
 * @Date: 2021-02-08
 * @Version： 1.0
 */
@Data
public class ChatImMess {

    private ImUser mine;
    private ImTo to;

}
