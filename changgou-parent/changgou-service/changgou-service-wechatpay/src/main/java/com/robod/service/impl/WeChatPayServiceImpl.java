package com.robod.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.robod.entity.MyWXPayConfig;
import com.robod.order.api.pojo.Order;
import com.robod.service.intf.WeChatPayService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Robod
 * @date 2020/9/1 0:03
 */
@Service("weChatPayService")
public class WeChatPayServiceImpl implements WeChatPayService {

    @Value("${wechat.appid}")
    private String appId;

    @Value("${wechat.mch_id}")
    private String mcnId;

    @Value("${wechat.key}")
    private String key;

    @Value("${wechat.notify_url}")
    private String notifyUrl;

    @Override
    public Map<String, String> createNative(Order order) {
        try {
            Map<String, String> map = new HashMap<>(16);
            map.put("body", "腾讯充值中心-QQ会员充值");    //商品描述
            map.put("out_trade_no", order.getId());      //商户订单号
            map.put("total_fee", String.valueOf((int)(order.getTotalMoney() * 100))); //标价金额,单位为分
            map.put("spbill_create_ip", "127.0.0.1");    //终端IP
            map.put("trade_type", "NATIVE ");    //交易类型，JSAPI -JSAPI支付,NATIVE -Native支付,APP -APP支付

            MyWXPayConfig config = new MyWXPayConfig(appId,mcnId, key);
            WXPay wxpay = new WXPay(config,notifyUrl);
            Map<String, String> response = wxpay.unifiedOrder(map);
            if (response == null || response.size() == 0) {
                throw new RuntimeException("下单失败");
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, String> queryPayStatus(String outTradeNo) {
        try {
            Map<String, String> map = new HashMap<>(16);
            map.put("out_trade_no", outTradeNo);      //商户订单号
            MyWXPayConfig config = new MyWXPayConfig(appId,mcnId, key);
            WXPay wxpay = new WXPay(config,notifyUrl);
            Map<String, String> response = wxpay.orderQuery(map);
            if (response == null || response.size() == 0) {
                throw new RuntimeException("订单查询失败");
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
