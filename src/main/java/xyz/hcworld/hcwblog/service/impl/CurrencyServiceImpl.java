package xyz.hcworld.hcwblog.service.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import xyz.hcworld.hcwblog.service.CurrencyService;
import xyz.hcworld.hcwblog.util.ConstantUtil;
import xyz.hcworld.hcwblog.util.ValidationUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: CurrencyServiceImpl
 * @Author: 张红尘
 * @Date: 2021-01-13
 * @Version： 1.0
 */
@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Override
    public String checkObjectIsNull(Object obj) {
        // 检查是否为空
        ValidationUtil.ValidResult validResult = ValidationUtil.validateBean(obj);
        if (validResult.hasErrors()) {
            //异常信息
            return validResult.getErrors();
        }
        return null;
    }

    @Override
    public boolean checkVercode(HttpServletRequest req, String vercode) {
        // 获取session的验证码
        String capthca = (String) req.getSession().getAttribute(ConstantUtil.KAPTCHA_SESSION_KEY);
        // 判空以及判断是否一致
        return StrUtil.isEmpty(capthca) || !capthca.equalsIgnoreCase(vercode);
    }
}
