package com.robod.order.api.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/****
 * @Author:admin
 * @Description:OrderConfig构建
 * @Date 2019/6/14 19:13
 *****/
@Data
@Table(name="tb_order_config")
public class OrderConfig implements Serializable{

	@Id
    @Column(name = "id")
	private Integer id;//ID

    @Column(name = "order_timeout")
	private Integer orderTimeout;//正常订单超时时间（分）

    @Column(name = "seckill_timeout")
	private Integer seckillTimeout;//秒杀订单超时时间（分）

    @Column(name = "take_timeout")
	private Integer takeTimeout;//自动收货（天）

    @Column(name = "service_timeout")
	private Integer serviceTimeout;//售后期限

    @Column(name = "comment_timeout")
	private Integer commentTimeout;//自动五星好评

}
