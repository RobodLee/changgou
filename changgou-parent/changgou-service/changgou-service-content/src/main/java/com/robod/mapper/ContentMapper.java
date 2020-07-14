package com.robod.mapper;
import com.robod.content.pojo.Content;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/****
 * @author robod
 * @Description:Content的Dao
 *****/
@Repository("contentMapper")
public interface ContentMapper {

    /**
     * 根据分类的ID 获取该分类下的所有的广告的列表
     * @param id
     * @return
     */
    @Select("select * from tb_content where category_id = #{id} and status = 1")
    List<Content> findByCategoryId(long id);
}
