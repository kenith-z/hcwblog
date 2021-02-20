package xyz.hcworld.hcwblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.hcworld.hcwblog.entity.Post;
import xyz.hcworld.hcwblog.search.model.PostDocument;
import xyz.hcworld.hcwblog.search.mq.PostMqIndexMessage;
import xyz.hcworld.hcwblog.search.repository.PostRepository;
import xyz.hcworld.hcwblog.service.PostService;
import xyz.hcworld.hcwblog.service.SearchService;
import xyz.hcworld.hcwblog.vo.PostVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索引擎服务
 *
 * @ClassName: SearchServiceImpl
 * @Author: 张红尘
 * @Date: 2021-02-02
 * @Version： 1.0
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Autowired
    ModelMapper modelMapper;

    /**
     * 搜索
     *
     * @param page    分页信息
     * @param keyword 关键词
     * @return
     */
    @Override
    public IPage search(Page page, String keyword) {
        PostDocument postDocument = new PostDocument(0L, keyword, null, keyword,
                null, null, keyword, null,
                null, null, null, null);
        Long current = page.getCurrent() - 1;
        Long size = page.getSize();
        String[] fields = new String[]{"title", "authorName", "categoryName"};
        //分页信息
        Pageable pageable = PageRequest.of(current.intValue(), size.intValue());
        //搜素es得到page

        org.springframework.data.domain.Page<PostDocument> documents = postRepository.findByTitleOrAuthorNameOrCategoryName(keyword, keyword, keyword, pageable);

        //结果信息 hibernate的page转mybatis plus的page
        IPage pageDate = new Page(page.getCurrent(), page.getSize(), documents.getTotalElements());
        pageDate.setRecords(documents.getContent());

        return pageDate;
    }

    @Override
    public int initEsData(List<PostVo> records) {
        if (records == null || records.isEmpty()) {
            return 0;
        }
        List<PostDocument> documents = new ArrayList<>(records.size());
        for (PostVo vo : records) {
            //映射转换
            PostDocument document = modelMapper.map(vo, PostDocument.class);
            documents.add(document);
        }
        postRepository.saveAll(documents);

        return documents.size();
    }

    @Override
    public void createOrUpdateIndex(PostMqIndexMessage message) {
        Long postId = message.getPostId();
        PostVo postVo = postService.selectOnePost(new QueryWrapper<Post>().eq("p.id", postId));
        //映射转换
        PostDocument document = modelMapper.map(postVo, PostDocument.class);
        postRepository.save(document);
        log.info("es索引更新成功！---{}", document.toString());
    }

    @Override
    public void removeIndex(PostMqIndexMessage message) {
        Long postId = message.getPostId();
        postRepository.deleteById(postId);
        log.info("es索引删除成功！---{}", message.toString());
    }
}
