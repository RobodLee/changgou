package com.robod.order.api.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/****
 * @Author:admin
 * @Description:ReturnCause构建
 * @Date 2019/6/14 19:13
 *****/
@Data
@Table(name="tb_return_cause")
public class ReturnCause implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;//ID

    @Column(name = "cause")
	private String cause;//原因

    @Column(name = "seq")
	private Integer seq;//排序

    @Column(name = "status")
	private String status;//是否启用

}
