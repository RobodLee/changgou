package com.robod.seckill.task;

import com.robod.entity.IdWorker;
import com.robod.entity.SystemConstants;
import com.robod.seckill.mapper.SeckillGoodsMapper;
import com.robod.seckill.pojo.SeckillGoods;
import com.robod.seckill.pojo.SeckillOrder;
import com.robod.seckill.pojo.SeckillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Robod
 * @date 2020/9/15 8:56
 */
@Component
public class MultiThreadingCreateOrder {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    /**
     * 异步抢单
     */
    @Async  //声明该方法是个异步任务，另开一个线程去运行
    public void createOrder() {
        //从redis队列中取出seckillStatus
        SeckillStatus seckillStatus = (SeckillStatus) redisTemplate.boundListOps(SystemConstants.SEC_KILL_USER_QUEUE_KEY).rightPop();

        BoundHashOperations seckillGoodsBoundHashOps = redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + seckillStatus.getTime());
        SeckillGoods seckillGoods = (SeckillGoods)seckillGoodsBoundHashOps.get(seckillStatus.getGoodsId());   //从redis中查询出秒杀商品
        if (seckillGoods == null || seckillGoods.getStockCount() <=0 ) {
            throw new RuntimeException("已售罄");
        }
        //创建秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setSeckillId(seckillGoods.getId());
        seckillOrder.setMoney(seckillGoods.getCostPrice());
        seckillOrder.setUserId(seckillStatus.getUsername());
        seckillOrder.setCreateTime(LocalDateTime.now());
        seckillOrder.setStatus("0");
        //将秒杀订单存入redis，键为用户名，确保一个用户只有一个秒杀订单
        redisTemplate.boundHashOps(SystemConstants.SEC_KILL_ORDER_KEY).put(seckillStatus.getUsername(),seckillOrder);

        //减库存，如果库存没了就从redis中删除，并将库存数据写到MySQL中
        seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
        if (seckillGoods.getStockCount() <= 0) {
            seckillGoodsBoundHashOps.delete(seckillStatus.getGoodsId());
            seckillGoodsMapper.updateByPrimaryKeySelective(seckillGoods);
        } else {
            seckillGoodsBoundHashOps.put(seckillStatus.getGoodsId(),seckillGoods);
        }
        //下单成功，更改seckillstatus的状态，再存入redis中
        seckillStatus.setOrderId(seckillOrder.getId());
        seckillStatus.setMoney(Float.valueOf(seckillGoods.getCostPrice()));
        seckillStatus.setStatus(2);
        redisTemplate.boundHashOps(SystemConstants.SEC_KILL_USER_STATUS_KEY).put(seckillStatus.getUsername(),seckillStatus);
    }

}
