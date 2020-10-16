package xyz.hcworld.hcwblog.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.hcworld.hcwblog.config.CloudStorageConfig;

import java.io.InputStream;


/**
 * 七牛云上传工具类
 *
 * @ClassName: QiniuUtil
 * @Author: 张红尘
 * @Date: 2020/10/10 16:22
 * @Version： 1.0
 */
@Slf4j
@Component
public class QiniuUtil {

    /**
     * 七牛文件上传管理器
     */
    private UploadManager uploadManager;
    private String token;
    /**
     * 文件删除
     */
    private BucketManager bucketManager;

    /**
     * 七牛认证管理
     **/
    private Auth auth;
    protected CloudStorageConfig config;

    /**
     * 上传图片工具类默认构造方法
     * @param config
     */
    public QiniuUtil(CloudStorageConfig config) {
        this.config = config;
        init();
    }

    public String getDomain(){
        return config.getQiniuDomain();
    }

    private void init() {
        // 构造一个带指定Zone对象的配置类, 注意这里的Region需要根据主机选择
        /*
            华东	Region.region0(), Region.huadong()
            华北	Region.region1(), Region.huabei()
            华南	Region.region2(), Region.huanan()
            北美	Region.regionNa0(), Region.beimei()
            东南亚	Region.regionAs0(), Region.xinjiapo()
         */
        Configuration cfg = new Configuration(Region.huanan());
        auth = Auth.create(config.getQiniuAccessKey(), config.getQiniuSecretKey());
        uploadManager = new UploadManager(cfg);
        bucketManager = new BucketManager(auth, cfg);
        // 根据命名空间生成的上传token
        token = auth.uploadToken(config.getQiniuBucketName());
    }

    /**
     * 上传图片
     * @param file
     * @param key
     * @return
     */
    public String uploadQiNiuImg(InputStream file, String key) {
        try {
            // 上传图片文件
            Response res = uploadManager.put(file, key, token, null, null);
            if (!res.isOK()) {
                throw new RuntimeException("上传七牛出错：" + res.toString());
            }
            // 解析上传成功的结果
            DefaultPutRet putRet = new ObjectMapper().readValue(res.bodyString(), DefaultPutRet.class);

            String path = config.getQiniuDomain() + "/" + putRet.key;
            // 这个returnPath是获得到的外链地址,通过这个地址可以直接打开图片
            log.info("写入一张图片({})", path);
            return path;
        } catch (QiniuException | JsonProcessingException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return "";
    }


    /**
     * 通过key删除七牛云上的图片
     */
    public void deleteQiNiuImg(String key){
        try {
            bucketManager.delete(config.getQiniuBucketName(), key);
        } catch (QiniuException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

}
