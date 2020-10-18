package com.robod.seckill.listener;

import com.alibaba.fastjson.JSON;
import com.robod.seckill.service.SeckillOrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Robod
 * @date 2020/10/15 16:57
 */
@Component
@RabbitListener(queues = "${mq.pay.queue.seckillorder}")
public class SeckillMessageListener {

    @Autowired
    private SeckillOrderService seckillOrderService;

    //https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_7&index=8
    @RabbitHandler
    public void getMessage(String message) {
        try {
            Map<String, String> resultMap = JSON.parseObject(message,Map.class);
            String returnCode = resultMap.get("return_code");   //状态码
            if ("SUCCESS".equals(returnCode)) {
                String resultCode = resultMap.get("result_code");   //业务结果
                String attach = resultMap.get("attach");
                Map<String,String> attachMap = JSON.parseObject(attach,Map.class);
                if ("SUCCESS".equals(resultCode)) {
                    //改订单状态
                    seckillOrderService.updatePayStatus(attachMap.get("username"),
                            resultMap.get("transaction_id"),resultMap.get("time_end"));
                } else {
                    //删除订单
                    seckillOrderService.deleteOrder(attachMap.get("username"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
