package xyz.hcworld.hcwblog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.hcworld.hcwblog.vo.CommentVo;

/**
 * 主页的控制器
 *
 * @ClassName: IndexController
 * @Author: 张红尘
 * @Date: 2020/7/11 15:23
 * @Version： 1.0
 */
@Controller
public class IndexController extends BaseController {
    /**
     * 首页
     * @return
     */
    @GetMapping({"", "/", "index"})
    public String index() {

        //1分页信息，2分类，3用户信息，4置顶，5精选，6排序
        IPage<CommentVo> results= postService.paging(getPage(),null,null,null,null,"created");
        req.setAttribute("pageData",results);
        req.setAttribute("currentCategoryId",0);
        return "index";
    }

    /**
     * 搜索
     * @return
     */
    @GetMapping("/search")
    public String search (String q){
       IPage pageData=  searchService.search(getPage(),q);
        req.setAttribute("currentCategoryId",0);
        req.setAttribute("q",q);
        req.setAttribute("pageData",pageData);
        return "/search";
    }

}
