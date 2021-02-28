package xyz.hcworld.hcwblog.util;

import cn.hutool.crypto.SmUtil;

/**
 * 加密工具类
 * @ClassName: KeyUtil
 * @Author: 张红尘
 * @Date: 2020/9/19 13:59
 * @Version： 1.0
 */
public class KeyUtil {



    /**
     * 加密
     * @param content 内容
     * @param iv 偏移量
     * @return 加密文
     */
    public static String  encryption(String content,String iv){
        return SmUtil.sm3(iv+content);
    }

}
