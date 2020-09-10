package com.robod.order.listener;

import com.github.wxpay.sdk.WXPayUtil;
import com.robod.order.service.intf.OrderService;
import com.robod.wechatpay.feign.WeChatPayFeign;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Robod
 * @date 2020/9/8 17:28
 */
@Component
@RabbitListener(queues = {"queue.order"})
public class OrderMessageListener {

    private final static String SUCCESS = "SUCCESS";

    @Autowired
    private OrderService orderService;

    @Autowired
    private WeChatPayFeign weChatPayFeign;

    /**
     * 监听mq的消息
     * @param message xml格式的消息
     */
    @RabbitHandler
    public void getMessage(String message) throws Exception {
        Map<String, String> map = WXPayUtil.xmlToMap(message);
        String returnCode = map.getOrDefault("return_code","");     //返回状态码
        String resultCode = map.getOrDefault("result_code","");     //业务结果
        if (SUCCESS.equals(returnCode)) {   //支付成功，修改订单状态
            String outTradeNo = map.get("out_trade_no");    //商户订单号
            if (! SUCCESS.equals(resultCode)) { //交易失败，关闭订单，从数据库中将订单状态修改为支付失败，回滚库存
                Map<String, String> closeResult = weChatPayFeign.closeOrder(outTradeNo).getData();  //关闭订单时服务器返回的数据
                //如果错误代码为ORDERPAID则说明订单已经支付，当作正常订单处理,反之 回滚库存
                if (!("FAIL".equals(closeResult.get("result_code")) && "ORDERPAID".equals(closeResult.get("err_code")))) {
                    orderService.deleteOrder(outTradeNo);
                    return;
                }
            }
            String transactionId = map.get("transaction_id");   //微信支付订单号
            String timeEnd = map.get("time_end");               //支付完成时间
            orderService.updateStatus(outTradeNo,timeEnd,transactionId);
        }
    }

}
