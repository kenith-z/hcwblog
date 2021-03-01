package xyz.hcworld.hcwblog.service.aip.impl;

import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.hcworld.gotool.net.HttpRequest;
import xyz.hcworld.hcwblog.config.BaiduAipConfig;
import xyz.hcworld.hcwblog.service.aip.AuthService;
import xyz.hcworld.hcwblog.util.ConstantUtil;
import xyz.hcworld.hcwblog.util.RedisUtil;

/**
 * @ClassName: AuthServiceImpl
 * @Author: 张红尘
 * @Date: 2021-03-01
 * @Version： 1.0
 */
@Service
public class AuthServiceImpl implements AuthService {

    BaiduAipConfig config;

    @Autowired
    RedisUtil redisUtil;

    public AuthServiceImpl(BaiduAipConfig config){
        this.config = config;
    }

    /**
     * 获取权限token
     * @return 返回示例：
     * {
     * "access_token": "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
     * "expires_in": 2592000
     * }
     */
    @Override
    public String getAuth() {
        if (redisUtil.hasKey(ConstantUtil.BAIDU_TOKEN)){
                return String.valueOf(redisUtil.get(ConstantUtil.BAIDU_TOKEN));
        }

        String token = "access_token="+getAuth(config.getAppKey(), config.getSecretKey());

        redisUtil.set(ConstantUtil.BAIDU_TOKEN,token,2592000);
        return token;
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    private String getAuth(String ak, String sk) {
        // 获取token地址
        try {
            String result = HttpRequest.sendGet("https://aip.baidubce.com/oauth/2.0/token","grant_type=client_credentials","client_id="+ak,"client_secret=" + sk);
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getStr("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }

}
