package xyz.hcworld.hcwblog.search.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * es的文章模型
 * @ClassName: PostDocment
 * @Author: 张红尘
 * @Date: 2021-02-01
 * @Version： 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "post")
public class PostDocument implements Serializable {
    /**
     * 文章id
     */
    @Id
    private Long id;
    /**
     * 文章标题
     * 使用ik分词器
     */
    @Field(type = FieldType.Text,searchAnalyzer="ik_smart",analyzer = "ik_max_word")
    private String title;
    /**
     * 作者id
     */
    @Field(type = FieldType.Long)
    private Long authorId;
    /**
     * 作者昵称
     * 关键字模式，不分词
     */
    @Field(type = FieldType.Keyword)
    private String authorName;
    /**
     * 作者头像
     */
    private String authorAvatar;
    /**
     * 归属分类
     */
    private Long categoryId;
    /**
     * 分类名
     * 关键字模式，不分词
     */
    @Field(type = FieldType.Keyword)
    private String categoryName;
    /**
     * 置顶
     */
    private Integer level;
    /**
     * 精华
     */
    private Boolean recommend;
    /**
     * 评论数量
     */
    private Integer commentCount;
    /**
     * 阅读数
     */
    private Integer viewCount;
    /**
     * 创建时间
     */
    @Field(type = FieldType.Date,format = DateFormat.basic_date_time)
    private Date created;

}
