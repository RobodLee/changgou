package com.robod.seckill.timer;

import com.robod.entity.DateUtil;
import com.robod.entity.SystemConstants;
import com.robod.seckill.mapper.SeckillGoodsMapper;
import com.robod.seckill.pojo.SeckillGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Robod
 * @date 2020/9/12 22:12
 */
@Component
public class SeckillGoodsPushTask {

    @Autowired
    private SeckillGoodsMapper mapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 定时将秒杀商品加载到redis中
     * 秒 分 小时 日 周 月
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void loadGoodsPushRedis() {
        List<Date> dateMenu = DateUtil.getDateMenus();
        for (Date date : dateMenu) {
            date.setYear(2019-1900);    //2019-6-1 为了方便测试
            date.setMonth(6-1);
            date.setDate(1);
            String dateString = SystemConstants.SEC_KILL_GOODS_PREFIX +DateUtil.data2str(date,"yyyyMMddHH");
            BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(dateString);
            Set<Long> keys = boundHashOperations.keys();
            List<SeckillGoods> seckillGoods;
            if (keys!=null && keys.size()>0) {
                seckillGoods = mapper.findSeckillGoodsNotIn(date,keys);
            } else {
                 seckillGoods = mapper.findSeckillGoods(date);
            }
            for (SeckillGoods seckillGood : seckillGoods) {
                boundHashOperations.put(seckillGood.getId(),seckillGood);   //把商品存入到redis

                redisTemplate.boundListOps(SystemConstants.SEC_KILL_GOODS_COUNT_LIST + seckillGood.getId())
                        .leftPushAll(getGoodsNumber(seckillGood.getNum()));
            }
        }
    }

    /**
     * 获取秒杀商品数量的数组
     * @return
     * @param num
     */
    public Byte[] getGoodsNumber(int num) {
        Byte[] arr = new Byte[num];
        for (int i = 0; i < num; i++) {
            arr[i] = '0';
        }
        return arr;
    }

}
