package com.robod.order.mapper;
import com.robod.order.api.pojo.Order;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:Order的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("orderMapper")
public interface OrderMapper extends Mapper<Order> {

    /**
     * 根据订单的id查询订单
     * @param outTradeNo
     * @return
     */
    @Select("select * from tb_order where id = #{outTradeNo} limit 1")
    Order findById(String outTradeNo);

}
