package com.robod.order.api.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/****
 * @Author:admin
 * @Description:Order构建
 * @LocalDateTime 2019/6/14 19:13
 *****/
@Data
@Table(name="tb_order")
public class Order implements Serializable{

	@Id
    @Column(name = "id")
	private String id;//订单id

    @Column(name = "total_num")
	private Integer totalNum;//数量合计

    @Column(name = "total_money")
	private Double totalMoney;//金额合计

    @Column(name = "pre_money")
	private Integer preMoney;//优惠金额

    @Column(name = "post_fee")
	private Integer postFee;//邮费

    @Column(name = "pay_money")
	private Integer payMoney;//实付金额

    @Column(name = "pay_type")
	private String payType;//支付类型，1、在线支付、0 货到付款

    @Column(name = "create_time")
	private LocalDateTime createTime;//订单创建时间

    @Column(name = "update_time")
	private LocalDateTime updateTime;//订单更新时间

    @Column(name = "pay_time")
	private LocalDateTime payTime;//付款时间

    @Column(name = "consign_time")
	private LocalDateTime consignTime;//发货时间

    @Column(name = "end_time")
	private LocalDateTime endTime;//交易完成时间

    @Column(name = "close_time")
	private LocalDateTime closeTime;//交易关闭时间

    @Column(name = "shipping_name")
	private String shippingName;//物流名称

    @Column(name = "shipping_code")
	private String shippingCode;//物流单号

    @Column(name = "username")
	private String username;//用户名称

    @Column(name = "buyer_message")
	private String buyerMessage;//买家留言

    @Column(name = "buyer_rate")
	private String buyerRate;//是否评价

    @Column(name = "receiver_contact")
	private String receiverContact;//收货人

    @Column(name = "receiver_mobile")
	private String receiverMobile;//收货人手机

    @Column(name = "receiver_address")
	private String receiverAddress;//收货人地址

    @Column(name = "source_type")
	private String sourceType;//订单来源：1:web，2：app，3：微信公众号，4：微信小程序  5 H5手机页面

    @Column(name = "transaction_id")
	private String transactionId;//交易流水号

    @Column(name = "order_status")
	private String orderStatus;//订单状态,0:未完成,1:已完成，2：已退货

    @Column(name = "pay_status")
	private String payStatus;//支付状态,0:未支付，1：已支付，2：支付失败

    @Column(name = "consign_status")
	private String consignStatus;//发货状态,0:未发货，1：已发货，2：已收货

    @Column(name = "is_delete")
	private String isDelete;//是否删除

	private List<Long> skuIds;	//选中的sku的id

}
