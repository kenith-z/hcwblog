package xyz.hcworld.hcwblog.util;

import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.hcworld.gotool.net.HttpRequest;
import xyz.hcworld.hcwblog.service.aip.AuthService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;

/**
 * @ClassName: BaiduCensorUtil
 * @Author: 张红尘
 * @Date: 2021-03-01
 * @Version： 1.0
 */
@Component
public class BaiduCensorUtil {
    /**
     * 图像处理请求
     */
    private static String IMG_URL = "https://aip.baidubce.com/rest/2.0/solution/v1/img_censor/v2/user_defined";
    /**
     * 文本处理请求
     */
    private static String TEXT_URL = "https://aip.baidubce.com/rest/2.0/solution/v1/text_censor/v2/user_defined";
    @Autowired
    AuthService authService;

    /**
     * 图像审核
     *
     * @param inputStream 文件流
     * @return
     */
    public boolean imageCensor(InputStream inputStream) {
        try {
            byte[] imgData = toByteArray(inputStream);
            String imgStr = Base64.getEncoder().encodeToString(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam;
            //获取accessToken
            String accessToken = authService.getAuth();
            String result = HttpRequest.sendPost(IMG_URL, accessToken, param);
            return new JSONObject(result).getInt(ConstantUtil.CONCLUSION_TYPE) != 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 图像审核
     *
     * @param images 图片链接列表
     * @return
     */
    public boolean imagesCensor(List<String> images) {
        try {
            for (String img : images) {
                //获取accessToken
                String accessToken = authService.getAuth();
                String result = HttpRequest.sendPost(IMG_URL, accessToken, "imgUrl=" + URLEncoder.encode(img, "UTF-8"));
                System.out.println(result);
                if (new JSONObject(result).getInt(ConstantUtil.CONCLUSION_TYPE) != 1) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 文本审核
     */
    public boolean textCensor(String content) {
        try {
            String param = "text=" + URLEncoder.encode(content, "UTF-8");
            //获取accessToken
            String accessToken = authService.getAuth();
            String result = HttpRequest.sendPost(TEXT_URL, accessToken, param);
            return new JSONObject(result).getInt(ConstantUtil.CONCLUSION_TYPE) != 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    private static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }


}
