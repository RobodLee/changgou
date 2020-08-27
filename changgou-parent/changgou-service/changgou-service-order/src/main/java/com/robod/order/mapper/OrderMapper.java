package com.robod.order.mapper;
import com.robod.order.api.pojo.Order;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:Order的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("orderMapper")
public interface OrderMapper extends Mapper<Order> {
}
