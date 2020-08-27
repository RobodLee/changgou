package com.robod.order.api.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/****
 * @Author:admin
 * @Description:ReturnOrderItem构建
 * @Date 2019/6/14 19:13
 *****/
@Data
@Table(name="tb_return_order_item")
public class ReturnOrderItem implements Serializable{

	@Id
    @Column(name = "id")
	private Long id;//ID

    @Column(name = "category_id")
	private Long categoryId;//分类ID

    @Column(name = "spu_id")
	private Long spuId;//SPU_ID

    @Column(name = "sku_id")
	private Long skuId;//SKU_ID

    @Column(name = "order_id")
	private Long orderId;//订单ID

    @Column(name = "order_item_id")
	private Long orderItemId;//订单明细ID

    @Column(name = "return_order_id")
	private Long returnOrderId;//退货订单ID

    @Column(name = "title")
	private String title;//标题

    @Column(name = "price")
	private Integer price;//单价

    @Column(name = "num")
	private Integer num;//数量

    @Column(name = "money")
	private Integer money;//总金额

    @Column(name = "pay_money")
	private Integer payMoney;//支付金额

    @Column(name = "image")
	private String image;//图片地址

    @Column(name = "weight")
	private Integer weight;//重量

}
