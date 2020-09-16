package com.robod.seckill.mapper;
import com.robod.seckill.pojo.SeckillOrder;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:SeckillOrder的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("seckillOrderMapper")
public interface SeckillOrderMapper extends Mapper<SeckillOrder> {
}
