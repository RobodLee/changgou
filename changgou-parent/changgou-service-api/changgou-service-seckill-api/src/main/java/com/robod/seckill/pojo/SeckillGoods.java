package com.robod.seckill.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author:admin
 * @Description:SeckillGoods构建
 * @Date 2019/6/14 19:13
 *****/
@Data
@Table(name="tb_seckill_goods")
public class SeckillGoods implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;//

    @Column(name = "sup_id")
	private Long supId;//spu ID

    @Column(name = "sku_id")
	private Long skuId;//sku ID

    @Column(name = "name")
	private String name;//标题

    @Column(name = "small_pic")
	private String smallPic;//商品图片

    @Column(name = "price")
	private String price;//原价格

    @Column(name = "cost_price")
	private String costPrice;//秒杀价格

    @Column(name = "create_time")
	private Date createTime;//添加日期

    @Column(name = "check_time")
	private Date checkTime;//审核日期

    @Column(name = "status")
	private String status;//审核状态，0未审核，1审核通过，2审核不通过

    @Column(name = "start_time")
	private Date startTime;//开始时间

    @Column(name = "end_time")
	private Date endTime;//结束时间

    @Column(name = "num")
	private Integer num;//秒杀商品数

    @Column(name = "stock_count")
	private Integer stockCount;//剩余库存数

    @Column(name = "introduction")
	private String introduction;//描述

}
