package xyz.hcworld.hcwblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ClassName: PostController
 * @Author: 张红尘
 * @Date: 2020/7/11 17:37
 * @Version： 1.0
 */
@Controller
public class PostController {
    /**
     * 博客分类
     * @return 博客分类的ftlh文件
     */
    @GetMapping("category/{id:\\d*}")
    public String category(@PathVariable(name = "id")  Long id) {
        return "post/category";
    }
    /**
     *
     * @return
     */
    @GetMapping("post/{id:\\d*}")
    public String detail(@PathVariable(name = "id")  Long id) {
        return "post/detail";
    }

}
