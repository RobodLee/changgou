package com.robod.order.mapper;
import com.robod.order.api.pojo.ReturnOrder;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:ReturnOrder的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("returnOrderMapper")
public interface ReturnOrderMapper extends Mapper<ReturnOrder> {
}
