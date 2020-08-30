package com.robod.order.mapper;

import com.robod.order.api.pojo.OrderItem;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:OrderItem的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("orderItemMapper")
public interface OrderItemMapper extends Mapper<OrderItem> {
}
