package com.robod.service.intf;

import com.robod.order.api.pojo.Order;

import java.util.Map;

/**
 * @author Robod
 * @date 2020/9/1 0:03
 */
public interface WeChatPayService {

    /**
     * 创建二维码
     * @param order
     * @return
     */
    Map<String,String> createNative(Order order);

    /**
     * 查询订单状态
     * @param outTradeNo 商户系统内部订单号
     * @return
     */
    Map<String,String> queryPayStatus(String outTradeNo);

    /**
     * 关闭订单
     * @param outTradeNo    商户订单号
     * @return
     * @throws Exception
     */
    Map<String, String> closeOrder(String outTradeNo) throws Exception;
}
