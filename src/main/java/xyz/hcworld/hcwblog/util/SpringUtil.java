package xyz.hcworld.hcwblog.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * Spring工具类
 * @ClassName: SpringUtil
 * @Author: 张红尘
 * @Date: 2021/2/18
 * @Version： 1.0
 */
@Component
public class SpringUtil implements ApplicationContextAware {


    private static ApplicationContext applicationContext = null;
    private static SpringUtil Instance = null;

    public synchronized static SpringUtil init() {
        if (Instance == null) {
            Instance = new SpringUtil();
        }
        return Instance;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

    public synchronized static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    /**
     * 发布消息
     * */
    public static void publish(ApplicationEvent event){
        applicationContext.publishEvent(event);
    }
}
