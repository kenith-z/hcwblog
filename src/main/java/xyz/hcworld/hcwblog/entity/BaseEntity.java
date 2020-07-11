package xyz.hcworld.hcwblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: BaseEntity
 * @Author: 张红尘
 * @Date: 2020/7/12 1:50
 * @Version： 1.0
 */
@Data
public class BaseEntity {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Date created;
    private Date modified;
}
