package xyz.hcworld.hcwblog.im.vo;

import lombok.Data;

/**
 * 到哪里去
 * @ClassName: ImTo
 * @Author: 张红尘
 * @Date: 2021-02-08
 * @Version： 1.0
 */
@Data
public class ImTo {

    /**
     * id
     */
    private Long id;
    /**
     * 昵称
     */
    private String name;
    /**
     * 成员
     */
    private Integer members;
    /**
     * 聊天类型：分friend和group两种，group即群聊
     */
    private String type;
    /**
     * 头像
     */
    private String avatar;

}
