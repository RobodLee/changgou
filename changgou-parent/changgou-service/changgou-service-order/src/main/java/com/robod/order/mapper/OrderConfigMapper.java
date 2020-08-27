package com.robod.order.mapper;
import com.robod.order.api.pojo.OrderConfig;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:OrderConfig的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("orderConfigMapper")
public interface OrderConfigMapper extends Mapper<OrderConfig> {
}
