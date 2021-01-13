package xyz.hcworld.hcwblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Kenith-Zhang
 * @since 2020-07-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("m_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 邮件
     */
    @Email
    @NotBlank(message = "邮箱不能为空")
    private String email;

    /**
     * 手机电话
     */
    private String mobile;

    /**
     * 积分
     */
    private Integer point;

    /**
     * 个性签名
     */
    private String sign;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * vip等级
     */
    private Integer vipLevel;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 内容数量
     */
    private Integer postCount;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 最后的登陆时间
     */
    private Date lasted;


}
