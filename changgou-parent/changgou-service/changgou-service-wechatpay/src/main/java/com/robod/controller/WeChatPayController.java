package com.robod.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayUtil;
import com.robod.entity.Result;
import com.robod.entity.StatusCode;
import com.robod.order.api.pojo.Order;
import com.robod.service.intf.WeChatPayService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 支付结果回调通知
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/notify/url")
    public String notifyUrl(HttpServletRequest request) throws Exception {
        ServletInputStream inputStream = request.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer))!=-1) {
            outputStream.write(buffer,0,len);
        }
        String xmlString = outputStream.toString("UTF-8");
        Map<String, String> xmlMap = WXPayUtil.xmlToMap(xmlString);
        String attach = xmlMap.get("attach");
        Map<String, String> attachMap = JSONObject.parseObject(attach, Map.class);

        //将java对象转换成amqp消息发送出去，调用的是send方法
        //rabbitTemplate.convertAndSend("exchange.order","routing.order", xmlString);
        rabbitTemplate.convertAndSend(attachMap.get("exchange"),attachMap.get("routingKey"), xmlString);

        return  "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }

    @RequestMapping("/notify/test")
    public String notifyTest(HttpServletRequest request) throws Exception {
        //将java对象转换成amqp消息发送出去，调用的是send方法
        rabbitTemplate.convertAndSend("exchange.order","routing.order", "hhhhh");
        return  "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }

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

    /**
     * 关闭订单
     * @param outTradeNo    商户订单号
     * @return
     */
    @RequestMapping("/cancel/order")
    public Result<Map<String,String>> closeOrder(String outTradeNo) throws Exception {
        Map<String,String> resultMap = weChatPayService.closeOrder(outTradeNo);
        return new Result<>(true, StatusCode.OK,"订单取消成功",resultMap);
    }

}
