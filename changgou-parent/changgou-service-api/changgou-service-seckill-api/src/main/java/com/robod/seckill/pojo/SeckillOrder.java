package com.robod.seckill.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/****
 * @Author:admin
 * @Description:SeckillOrder构建
 * @Date 2019/6/14 19:13
 *****/
@Data
@Table(name="tb_seckill_order")
public class SeckillOrder implements Serializable{

	@Id
    @Column(name = "id")
	private Long id;//主键

    @Column(name = "seckill_id")
	private Long seckillId;//秒杀商品ID

    @Column(name = "money")
	private String money;//支付金额

    @Column(name = "user_id")
	private String userId;//用户

    @Column(name = "create_time")
	private LocalDateTime createTime;//创建时间

    @Column(name = "pay_time")
	private Date payTime;//支付时间

    @Column(name = "status")
	private String status;//状态，0未支付，1已支付

    @Column(name = "receiver_address")
	private String receiverAddress;//收货人地址

    @Column(name = "receiver_mobile")
	private String receiverMobile;//收货人电话

    @Column(name = "receiver")
	private String receiver;//收货人

    @Column(name = "transaction_id")
	private String transactionId;//交易流水

}
