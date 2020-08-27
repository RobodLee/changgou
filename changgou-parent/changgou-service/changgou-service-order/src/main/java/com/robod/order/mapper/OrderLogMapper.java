package com.robod.order.mapper;
import com.robod.order.api.pojo.OrderLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:OrderLog的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("orderLogMapper")
public interface OrderLogMapper extends Mapper<OrderLog> {
}
