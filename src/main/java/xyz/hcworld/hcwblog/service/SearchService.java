package xyz.hcworld.hcwblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyz.hcworld.hcwblog.search.mq.PostMqIndexMessage;
import xyz.hcworld.hcwblog.vo.PostVo;

import java.util.List;

/**
 * @ClassName: SearchService
 * @Author: 张红尘
 * @Date: 2021-02-02
 * @Version： 1.0
 */
public interface SearchService {
    /**
     *  搜索
     * @param page 分页信息
     * @param keyword 关键词
     * @return
     */
    IPage search(Page page, String keyword);

    /**
     * 初始化es数据
     * @param records 文章信息
     * @return
     */
    int initEsData(List<PostVo> records);

    /**
     * 消息队列处理发表文章以及修改文章
     * @param message 自定义消息对象
     */
    void createOrUpdateIndex(PostMqIndexMessage message);

    /**
     * 消息队列处理删除文章
     * @param message 自定义消息对象
     */
    void removeIndex(PostMqIndexMessage message);
}
