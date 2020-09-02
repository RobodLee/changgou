package com.robod.controller;

import com.robod.entity.Result;
import com.robod.entity.StatusCode;
import com.robod.order.api.pojo.Order;
import com.robod.service.intf.WeChatPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Robod
 * @date 2020/9/1 0:02
 */
@RestController
@RequestMapping("/wechat/pay")
public class WeChatPayController {

    @Autowired
    private WeChatPayService weChatPayService;

    /***
     * 创建二维码
     * @return
     */
    @RequestMapping(value = "/create/native")
    public Result<Map<String,String>> createNative(@RequestBody Order order){
        Map<String,String> resultMap = weChatPayService.createNative(order);
        return new Result<>(true, StatusCode.OK,"创建二维码预付订单成功！",resultMap);
    }

    /**
     * 查询订单状态
     * @param outTradeNo 商户系统内部订单号
     * @return
     */
    @GetMapping(value = "/status/query")
    public Result<Map<String,String>> queryPayStatus(@RequestParam String outTradeNo) {
        Map<String,String> resultMap = weChatPayService.queryPayStatus(outTradeNo);
        return new Result<>(true, StatusCode.OK,"订单查询成功",resultMap);
    }

}
