package com.robod.mapper.tk_mapper;

import com.robod.goods.pojo.Category;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Robod
 * @date 2020/7/8 7:18
 */
@Repository("categoryMapper")
public interface CategoryMapper extends Mapper<Category> {
}
