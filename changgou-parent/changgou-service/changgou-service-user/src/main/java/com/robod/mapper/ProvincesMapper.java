package com.robod.mapper;
import com.robod.user.pojo.Provinces;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:Provinces的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("provincesMapper")
public interface ProvincesMapper extends Mapper<Provinces> {
}
