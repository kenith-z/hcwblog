package xyz.hcworld.hcwblog.service;

import xyz.hcworld.hcwblog.commont.lang.Result;
import xyz.hcworld.hcwblog.entity.UserCollection;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kenith-Zhang
 * @since 2020-07-12
 */
public interface UserCollectionService extends IService<UserCollection> {

    /**
     * 增加收藏
     * @param pid 文章id
     * @param userId 要收藏文章的用户
     * @return
     */
    Result addCollection(Long pid, Long userId);

    /**
     * 取消收藏
     * @param pid 文章id
     * @param userId 要取消收藏文章的用户
     * @return
     */
    Result removeCollection(Long pid, Long userId);

    /**
     * 查询用户是否收藏该文章
     * @param pid 文章id
     * @param userId 要收藏文章的用户
     * @return
     */
    Result collectionExistence(Long pid, Long userId);

}
