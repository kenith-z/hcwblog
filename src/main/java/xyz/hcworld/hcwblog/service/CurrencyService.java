package xyz.hcworld.hcwblog.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 通用服务接口
 * @ClassName: CurrencyService
 * @Author: 张红尘
 * @Date: 2021-01-13
 * @Version： 1.0
 */
public interface CurrencyService {

    /**
     * 判断传入参数是否为空
     * @param obj 传入的参数
     * @return 为null则
     */
    String checkObjectIsNull(Object obj);
    /**
     * 判断验证码是否正确
     * @param req 获取session
     * @param vercode 前段传来的验证码
     * @return 为null则
     */
    boolean checkVercode(HttpServletRequest req,String vercode);
}
