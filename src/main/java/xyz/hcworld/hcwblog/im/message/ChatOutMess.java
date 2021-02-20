package xyz.hcworld.hcwblog.im.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.hcworld.hcwblog.im.vo.ImMess;

/**
 * IM的输出消息
 * @ClassName: ChatOutMess
 * @Author: 张红尘
 * @Date: 2021-02-09
 * @Version： 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatOutMess {
    /**
     * 消息类型
     */
    private String emit;
    /**
     * 数据
     */
    private ImMess data;

}
