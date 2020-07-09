package com.robod.mapper;

import com.robod.goods.pojo.Category;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Robod
 * @date 2020/7/8 7:18
 */
@Repository("categoryMapper")
public interface CategoryMapper extends Mapper<Category> {

    /**
     * 根据父id查询zi分类集合
     * @param pid
     * @return
     */
    @Select("SELECT * FROM tb_category WHERE parent_id = #{id}")
    public List<Category> findByParentId(Integer pid);

    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    @Select("select * from tb_category where id=#{id} limit 1")
    public Category findById(Integer id);
}
