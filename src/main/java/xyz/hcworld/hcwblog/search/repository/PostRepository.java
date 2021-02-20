package xyz.hcworld.hcwblog.search.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import xyz.hcworld.hcwblog.search.model.PostDocument;

/**
 * 文章仓库
 * 使用hql标准
 * @ClassName: PostRepository
 * @Author: 张红尘
 * @Date: 2021-02-01
 * @Version： 1.0
 */
@Repository
public interface PostRepository extends ElasticsearchRepository<PostDocument,Long> {


    Page<PostDocument> findByTitleOrAuthorNameOrCategoryName(String title,String authorName,String categoryName, Pageable pageable);
}
