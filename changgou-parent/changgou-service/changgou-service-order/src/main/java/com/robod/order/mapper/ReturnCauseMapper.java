package com.robod.order.mapper;
import com.robod.order.api.pojo.ReturnCause;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:ReturnCause的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("returnCauseMapper")
public interface ReturnCauseMapper extends Mapper<ReturnCause> {
}
