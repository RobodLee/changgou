package com.robod.order.api.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author:admin
 * @Description:Preferential构建
 * @Date 2019/6/14 19:13
 *****/
@Data
@Table(name="tb_preferential")
public class Preferential implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;//ID

    @Column(name = "buy_money")
	private Integer buyMoney;//消费金额

    @Column(name = "pre_money")
	private Integer preMoney;//优惠金额

    @Column(name = "category_id")
	private Long categoryId;//品类ID

    @Column(name = "start_time")
	private Date startTime;//活动开始日期

    @Column(name = "end_time")
	private Date endTime;//活动截至日期

    @Column(name = "state")
	private String state;//状态

    @Column(name = "type")
	private String type;//类型1不翻倍 2翻倍

}
