package com.robod.seckill.service;

import com.github.pagehelper.PageInfo;
import com.robod.seckill.pojo.SeckillOrder;
import com.robod.seckill.pojo.SeckillStatus;

import java.util.List;

/****
 * @Author:admin
 * @Description:SeckillOrder业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface SeckillOrderService {

    /***
     * SeckillOrder多条件分页查询
     * @param seckillOrder
     * @param page
     * @param size
     * @return
     */
    PageInfo<SeckillOrder> findPage(SeckillOrder seckillOrder, int page, int size);

    /***
     * SeckillOrder分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<SeckillOrder> findPage(int page, int size);

    /***
     * SeckillOrder多条件搜索方法
     * @param seckillOrder
     * @return
     */
    List<SeckillOrder> findList(SeckillOrder seckillOrder);

    /***
     * 删除SeckillOrder
     * @param id
     */
    void delete(Long id);

    /***
     * 修改SeckillOrder数据
     * @param seckillOrder
     */
    void update(SeckillOrder seckillOrder);

    /***
     * 新增SeckillOrder
     * @param seckillOrder
     */
    void add(SeckillOrder seckillOrder);

    /**
     * 根据ID查询SeckillOrder
     * @param id
     * @return
     */
     SeckillOrder findById(Long id);

    /***
     * 查询所有SeckillOrder
     * @return
     */
    List<SeckillOrder> findAll();

    /**
     * 下单
     * @param id
     * @param time
     * @param username
     * @return
     */
    boolean add(Long id, String time, String username);

    /**
     * 查询状态
     * @param username
     * @return
     */
    SeckillStatus queryStatus(String username);

    /**
     * 支付成功后修改订单状态
     * @param username
     * @param transactionId
     * @param endTime
     */
    void updatePayStatus(String username,String transactionId,String endTime);

    /**
     * 删除秒杀订单
     * @param username
     */
    void deleteOrder(String username);
}
