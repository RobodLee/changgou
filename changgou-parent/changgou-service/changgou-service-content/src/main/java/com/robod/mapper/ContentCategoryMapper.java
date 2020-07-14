package com.robod.mapper;
import com.robod.content.pojo.ContentCategory;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:ContentCategory的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("contentCategoryMapper")
public interface ContentCategoryMapper extends Mapper<ContentCategory> {
}
