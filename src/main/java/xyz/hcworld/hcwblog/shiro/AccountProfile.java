package xyz.hcworld.hcwblog.shiro;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName: AccountProfile
 * @Author: 张红尘
 * @Date: 2020/9/19 17:04
 * @Version： 1.0
 */
@Data
public class AccountProfile implements Serializable {
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 简介
     */
    private String sign;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * vip等级
     */
    private Integer vipLevel;
    /**
     * 注册时间
     */
    private LocalDateTime created;

    public String getSex(){
        if (gender.equals(1)){
            return "男";
        }else if (gender.equals(2)){
            return "女";
        }else {
            return "保密";
        }
    }
}
