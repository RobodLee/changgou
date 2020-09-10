package com.robod.wechatpay.feign;

import com.robod.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author Robod
 * @date 2020/9/8 22:45
 */
@FeignClient("wechatpay")
@RequestMapping("/wechat/pay")
public interface WeChatPayFeign {

    /**
     * 关闭订单
     * @param outTradeNo    商户订单号
     * @return
     * @throws Exception
     */
    @RequestMapping("/cancel/order")
    Result<Map<String,String>> closeOrder(String outTradeNo) throws Exception;

}
