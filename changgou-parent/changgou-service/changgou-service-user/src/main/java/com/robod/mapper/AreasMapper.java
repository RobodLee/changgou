package com.robod.mapper;
import com.robod.user.pojo.Areas;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:Areas的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("areasMapper")
public interface AreasMapper extends Mapper<Areas> {
}
