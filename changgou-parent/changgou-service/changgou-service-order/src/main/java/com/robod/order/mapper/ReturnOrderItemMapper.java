package com.robod.order.mapper;
import com.robod.order.api.pojo.ReturnOrderItem;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:ReturnOrderItem的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("returnOrderItemMapper")
public interface ReturnOrderItemMapper extends Mapper<ReturnOrderItem> {

}
