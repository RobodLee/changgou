package com.robod.order.service.intf;

import com.robod.order.api.pojo.OrderItem;

import java.util.List;

/**
 * @author Robod
 * @date 2020/8/22 11:02
 * 购物车
 */
public interface CartService {

    /**
     * 添加到购物车
     * @param id
     * @param num
     * @param username
     */
    void add(long id, int num, String username);

    /**
     * 查询购物车数据
     * @param username  用户名
     * @return  订单集合数据
     */
    List<OrderItem> list(String username);
}
