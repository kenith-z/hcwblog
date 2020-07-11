package xyz.hcworld.hcwblog.service.impl;

import xyz.hcworld.hcwblog.entity.Category;
import xyz.hcworld.hcwblog.mapper.CategoryMapper;
import xyz.hcworld.hcwblog.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Kenith-Zhang
 * @since 2020-07-12
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
