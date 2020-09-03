package xyz.hcworld.hcwblog.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import xyz.hcworld.hcwblog.entity.Category;
import xyz.hcworld.hcwblog.service.CategoryService;
import xyz.hcworld.hcwblog.service.PostService;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * 项目启动就执行的类
 * @ClassName: ContextStartup
 * @Author: 张红尘
 * @Date: 2020/7/12 12:54
 * @Version： 1.0
 */
@Component
public class ContextStartup implements ApplicationRunner,ServletContextAware {
    /**
     *  菜单栏
     */
    @Autowired
    CategoryService categoryService;
    /**
     * servlet上下文
     */
    ServletContext servletContext;
    /**
     * 文章
     */
    @Autowired
    PostService postService;

    /**
     * 项目启动就执行<br>
     * 1.查询菜单栏的属性
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //加载菜单
       List<Category> category=  categoryService.list(new QueryWrapper<Category>()
            .eq("status",0)
        );
       servletContext.setAttribute("category",category);
        postService.initWeekRank();
    }

    /**
     * 获取servlet上下文
     * @param servletContext
     */
    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
