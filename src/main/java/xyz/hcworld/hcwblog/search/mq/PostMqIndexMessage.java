package xyz.hcworld.hcwblog.search.mq;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 文章的消息对象传递vo
 * @ClassName: PostMqIndexMessage
 * @Author: 张红尘
 * @Date: 2021-02-05
 * @Version： 1.0
 */
@Data
@AllArgsConstructor
public class PostMqIndexMessage  implements Serializable {


    /**
     * 要操作的文章id
     */
    private Long postId;
    /**
     * 操作类型
     */
    private String type;

}
