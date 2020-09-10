package com.robod.order.mapper;

import com.robod.order.api.pojo.OrderItem;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/****
 * @Author:admin
 * @Description:OrderItem的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("orderItemMapper")
public interface OrderItemMapper extends Mapper<OrderItem> {

    /**
     * 根据订单的id查询order_item集合
     * @param orderId
     * @return
     */
    @Select("select * from tb_order_item where order_id = #{orderId}")
    List<OrderItem> findByOrderId(String orderId);

}
