package xyz.hcworld.hcwblog.controller;

import cn.hutool.core.util.StrUtil;
import com.google.code.kaptcha.Producer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.hcworld.hcwblog.commont.lang.Result;
import xyz.hcworld.hcwblog.entity.User;
import xyz.hcworld.hcwblog.util.ConstantUtil;
import xyz.hcworld.hcwblog.util.KeyUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录注册控制器
 *
 * @ClassName: AuthController
 * @Author: 张红尘
 * @Date: 2020/9/5 22:30
 * @Version： 1.0
 */
@Controller
public class AuthController extends BaseController {
    @Autowired
    Producer producer;


    @GetMapping("/capthca{random}.jpg")
    public void kaptcha(HttpServletResponse response,@PathVariable Integer random) throws IOException {
        // 验证码字符串
        String text = producer.createText();
        // 创建验证码图片
        BufferedImage image = producer.createImage(text);
        req.getSession().setAttribute(ConstantUtil.KAPTCHA_SESSION_KEY, text);

        // 响应头设置不缓存
        response.setHeader("Cache-Control", "no-store, no-cache");
        // 响应内容为image
        response.setContentType("image/jpeg");
        // 输出流
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "jpg", outputStream);
    }


    /**
     * 注册请求
     *
     * @param user    表单是用户名以及密码邮箱等
     * @param repass  重复密码
     * @param vercode 验证码
     * @return
     */
    @ResponseBody
    @PostMapping("/register")
    public Result doRegister(User user, String repass, String vercode) {
        String objectIsNull = currencyService.checkObjectIsNull(user);
        if (objectIsNull!=null) {
            //异常信息
            return Result.fail(objectIsNull);
        }
        // 两次密码判断
        if (!user.getPassword().equals(repass)) {
            return Result.fail("两次密码不相同");
        }
        // 验证码判空以及判断是否一致
        if (currencyService.checkVercode(req,vercode)) {
            return Result.fail("验证码不正确");
        }
        // 完成注册
        Result result = userService.register(user);
        return result.action("/login");
    }

    /**
     * 登录请求
     *
     * @param email 邮箱
     * @param password 密码
     * @param vercode  验证码
     * @return
     */
    @ResponseBody
    @PostMapping("/login")
    public Result doLogin(String email, String password, String vercode) {
        //账号密码为空
        if (StrUtil.isEmpty(email) || StrUtil.isEmpty(password)) {
            return Result.fail("邮箱或密码不能为空");
        }
        // 验证码判空以及判断是否一致
        if (currencyService.checkVercode(req,vercode)) {
            return Result.fail("验证码不正确");
        }
        UsernamePasswordToken token = new UsernamePasswordToken(email, KeyUtil.encryption(password));
        try {
            SecurityUtils.getSubject().login(token);
        } catch (AuthenticationException e) {
            if (e instanceof UnknownAccountException) {
                return Result.fail("用户不存在");
            } else if (e instanceof LockedAccountException) {
                return Result.fail("用户被禁用");
            } else if (e instanceof IncorrectCredentialsException) {
                return Result.fail("密码错误");
            } else {
                return Result.fail("用户认证失败");
            }
        }

        return Result.success().action("/");
    }

    @GetMapping("/user/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "redirect:/";
    }

    /**
     * 登录页面
     *
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    /**
     * 注册页面
     *
     * @return
     */
    @GetMapping("/register")
    public String register() {
        return "auth/reg";
    }


}
