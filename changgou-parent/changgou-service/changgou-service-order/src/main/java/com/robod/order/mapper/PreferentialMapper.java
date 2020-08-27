package com.robod.order.mapper;
import com.robod.order.api.pojo.Preferential;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:Preferential的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("preferentialMapper")
public interface PreferentialMapper extends Mapper<Preferential> {
}
