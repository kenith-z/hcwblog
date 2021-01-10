package xyz.hcworld.hcwblog.shiro;

import cn.hutool.json.JSONUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.UserFilter;
import xyz.hcworld.hcwblog.commont.lang.Result;
import xyz.hcworld.hcwblog.util.ConstantUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 自定义shiro过滤器查看请求是否是ajax
 *
 * @ClassName: AuthFilter
 * @Author: 张红尘
 * @Date: 2021-01-07
 * @Version： 1.0
 */
public class AuthFilter extends UserFilter {


    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String header = httpServletRequest.getHeader("X-Requested-With");
        //AJAX 请求弹窗未登录
        if (ConstantUtil.XRW.equals(header)) {
            boolean authenticated = SecurityUtils.getSubject().isAuthenticated();
            if (!authenticated) {
                response.setContentType("application/json;charset=UTf-8");
                response.getWriter().print(JSONUtil.toJsonStr(Result.fail("请先登录！")));
            }
        } else {
            //web 请求重定向
            super.redirectToLogin(request, response);
        }
    }
}
