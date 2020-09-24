package xyz.hcworld.hcwblog.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import xyz.hcworld.hcwblog.commont.templates.DirectiveHandler;
import xyz.hcworld.hcwblog.commont.templates.TemplateDirective;
import xyz.hcworld.hcwblog.util.RedisUtil;

import java.util.*;

/**
 * 本周热议
 * @ClassName: RankTemplate
 * @Author: 张红尘
 * @Date: 2020/9/3 17:26
 * @Version： 1.0
 */
@Component
public class HotsTemplate extends TemplateDirective {
    @Autowired
    RedisUtil redisUtil;


    @Override
    public String getName() {
        return "hots";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        String weekRankKey = "week:rank";
        //获取热议集合
        Set<ZSetOperations.TypedTuple> typedTuples = redisUtil.getZSetRank(weekRankKey, 0, 6);

        List<Map> hosPosts = new ArrayList<>();
        Map<String,Object> map;
        for (ZSetOperations.TypedTuple typedTuple:typedTuples){
            map = new HashMap<>(5);
            //post的id
            Object id = typedTuple.getValue();
            //文章基本信息的key
            String postKey = "rank:post:" + id;
            map.put("id",id);
            map.put("title",redisUtil.hget(postKey,"post:title"));
            map.put("commentCount",typedTuple.getScore());

            hosPosts.add(map);
        }

        handler.put(RESULTS,hosPosts).render();
    }
}
