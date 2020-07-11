package xyz.hcworld.hcwblog.controller;

import org.springframework.beans.factory.annotation.Autowired;

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
}
