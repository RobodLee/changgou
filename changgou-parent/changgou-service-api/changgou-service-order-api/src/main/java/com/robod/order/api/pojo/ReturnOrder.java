package com.robod.order.api.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author:admin
 * @Description:ReturnOrder构建
 * @Date 2019/6/14 19:13
 *****/
@Data
@Table(name="tb_return_order")
public class ReturnOrder implements Serializable{

	@Id
    @Column(name = "id")
	private Long id;//服务单号

    @Column(name = "order_id")
	private Long orderId;//订单号

    @Column(name = "apply_time")
	private Date applyTime;//申请时间

    @Column(name = "user_id")
	private Long userId;//用户ID

    @Column(name = "user_account")
	private String userAccount;//用户账号

    @Column(name = "linkman")
	private String linkman;//联系人

    @Column(name = "linkman_mobile")
	private String linkmanMobile;//联系人手机

    @Column(name = "type")
	private String type;//类型

    @Column(name = "return_money")
	private Integer returnMoney;//退款金额

    @Column(name = "is_return_freight")
	private String isReturnFreight;//是否退运费

    @Column(name = "status")
	private String status;//申请状态

    @Column(name = "dispose_time")
	private Date disposeTime;//处理时间

    @Column(name = "return_cause")
	private Integer returnCause;//退货退款原因

    @Column(name = "evidence")
	private String evidence;//凭证图片

    @Column(name = "description")
	private String description;//问题描述

    @Column(name = "remark")
	private String remark;//处理备注

    @Column(name = "admin_id")
	private Integer adminId;//管理员id

}
