package xyz.hcworld.hcwblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import xyz.hcworld.hcwblog.service.CommentService;
import xyz.hcworld.hcwblog.service.PostService;

import javax.servlet.http.HttpServletRequest;

/**
 * 公共控制器
 * @ClassName: BaseController
 * @Author: 张红尘
 * @Date: 2020/7/12 1:23
 * @Version： 1.0
 */
public class BaseController {
    @Autowired
    HttpServletRequest req;
    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    /**
     * 设置分页信息
     * @return
     */
    public Page getPage(){
        //页数
        int pn = ServletRequestUtils.getIntParameter(req,"pn",1);
        //每页条数
        int size = ServletRequestUtils.getIntParameter(req,"size",2);
        return new Page(pn,size);
    }

}
