package xyz.hcworld.hcwblog.commont.exception;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import xyz.hcworld.gotool.date.DateTimeUtils;
import xyz.hcworld.hcwblog.commont.lang.Result;
import xyz.hcworld.hcwblog.util.ConstantUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局异常处理
 * @ClassName: GlobalExceptionHandler
 * @Author: 张红尘
 * @Date: 2021-01-11
 * @Version： 1.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView handle(HttpServletRequest req, HttpServletResponse resp,Exception e) throws IOException {
        //AJAX请求处理
        String header = req.getHeader(ConstantUtil.H_XRW);
        //AJAX 请求弹窗未登录
        if (ConstantUtil.XRW.equals(header)) {
            resp.setContentType("application/json;charset=UTf-8");
            resp.getWriter().print(JSONUtil.toJsonStr(Result.fail(DateTimeUtils.getCurrentTimeStr()+"("+e.getMessage()+")")));
            return null;
        }
        if(e instanceof NullPointerException){
            System.err.println(DateTimeUtils.getCurrentDateTimeStr() +"("+req.getRequestURI()+")"+e.getMessage());
        }
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("errMessage",e.getMessage());
        return mv;
    }


}
