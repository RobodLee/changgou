package com.robod.order.service.intf;

import com.github.pagehelper.PageInfo;
import com.robod.order.api.pojo.Order;

import java.util.List;

/****
 * @Author:admin
 * @Description:Order业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface OrderService {

    /**
     * 订单支付成功后修改订单的状态
     * @param outTradeNo    商户订单号
     * @param timeEnd       付款时间
     * @param transactionId 微信支付订单号-交易流水号
     */
    void updateStatus(String outTradeNo,String timeEnd,String transactionId);

    /**
     * 交易失败，删除订单，不是真的删除，只是将支付状态改为”2“，支付失败
     * @param outTradeNo
     */
    void deleteOrder(String outTradeNo);

    /***
     * Order多条件分页查询
     * @param order
     * @param page
     * @param size
     * @return
     */
    PageInfo<Order> findPage(Order order, int page, int size);

    /***
     * Order分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Order> findPage(int page, int size);

    /***
     * Order多条件搜索方法
     * @param order
     * @return
     */
    List<Order> findList(Order order);

    /***
     * 删除Order
     * @param id
     */
    void delete(String id);

    /***
     * 修改Order数据
     * @param order
     */
    void update(Order order);

    /***
     * 新增Order
     * @param order
     */
    void add(Order order);

    /**
     * 根据ID查询Order
     * @param id
     * @return
     */
     Order findById(String id);

    /***
     * 查询所有Order
     * @return
     */
    List<Order> findAll();
}
