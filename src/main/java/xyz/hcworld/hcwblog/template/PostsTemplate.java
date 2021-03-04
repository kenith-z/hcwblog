package xyz.hcworld.hcwblog.template;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.hcworld.hcwblog.commont.templates.DirectiveHandler;
import xyz.hcworld.hcwblog.commont.templates.TemplateDirective;
import xyz.hcworld.hcwblog.service.PostService;
import xyz.hcworld.hcwblog.util.ConstantUtil;

/**
 * 文章
 * @ClassName: PostsTemplate
 * @Author: 张红尘
 * @Date: 2020/7/13 23:38
 * @Version： 1.0
 */
@Component
public class PostsTemplate extends TemplateDirective {
    @Autowired
    PostService postService;

    @Override
    public String getName() {
        return "posts";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer level = handler.getInteger("level");
        Integer pn = handler.getInteger("pn", 1);
        Integer size = handler.getInteger("size", ConstantUtil.PAGE_SIZE);
        Long categoryId = handler.getLong("categoryId");
        Boolean recommend = handler.getBoolean("recommend");
        IPage page= postService.paging(new Page(pn,size),categoryId,null,level,recommend,"created");
        handler.put(RESULTS,page).render();
    }
}
