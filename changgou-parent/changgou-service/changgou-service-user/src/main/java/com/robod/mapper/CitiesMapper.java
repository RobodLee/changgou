package com.robod.mapper;
import com.robod.user.pojo.Cities;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:Cities的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("citiesMapper")
public interface CitiesMapper extends Mapper<Cities> {
}
