package xyz.hcworld.hcwblog.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import xyz.hcworld.hcwblog.entity.Category;
import xyz.hcworld.hcwblog.im.handel.MsgHandlerFactory;
import xyz.hcworld.hcwblog.service.CategoryService;
import xyz.hcworld.hcwblog.service.PostService;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static xyz.hcworld.hcwblog.util.ConstantUtil.*;

/**
 * 项目启动就执行的类
 *
 * @ClassName: ContextStartup
 * @Author: 张红尘
 * @Date: 2020/7/12 12:54
 * @Version： 1.0
 */
@Component
public class ContextStartup implements ApplicationRunner, ServletContextAware {
    /**
     * 菜单栏
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
     * 设置每页多少条数据
     */
    @Value("${mybatis-plus.size}")
    private Integer pageSize;

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }


    /**
     * 项目启动就执行<br>
     * 1.查询菜单栏的属性
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //加载菜单
        List<Category> category = categoryService.list(new QueryWrapper<Category>()
                .eq("status", 0)
        );
        servletContext.setAttribute("category", category);
        postService.initWeekRank();
        //初始化IM消息处理器类别
        MsgHandlerFactory.init();
        PAGE_SIZE = pageSize;
        /**
         * 定义群聊信息
         */
        Map<String,Object> group = new HashMap<>(5);
        group.put("name","社区群聊");
        group.put("type","group");
        group.put("avatar","https://img.hcworld.xyz/code/duck/2020-10-17-5bcbc3b8154f4ecea6fc711d1e4b7f89.jpg");
        group.put("id",IM_GROUP_ID);
        group.put("members",0);
        IM_GROUP_MAP = group;
    }

    /**
     * 获取servlet上下文
     *
     * @param servletContext
     */
    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
