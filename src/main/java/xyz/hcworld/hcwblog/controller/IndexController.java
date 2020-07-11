package xyz.hcworld.hcwblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 主页的控制器
 *
 * @ClassName: IndexController
 * @Author: 张红尘
 * @Date: 2020/7/11 15:23
 * @Version： 1.0
 */
@Controller
public class IndexController {
    /**
     * 首页
     * @return
     */
    @GetMapping({"", "/", "index"})
    public String index() {
        return "index";
    }


}
