package com.robod.user.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Robod
 * @date 2019/6/14 19:13
 * Address构建
 **/
@Data
@Table(name="tb_address")
public class Address implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;//

    @Column(name = "username")
	private String username;//用户名

    @Column(name = "province_id")
	private String provinceId;//省

    @Column(name = "city_id")
	private String cityId;//市

    @Column(name = "area_id")
	private String areaId;//县/区

    @Column(name = "phone")
	private String phone;//电话

    @Column(name = "address")
	private String address;//详细地址

    @Column(name = "contact")
	private String contact;//联系人

    @Column(name = "is_default")
	private String isDefault;//是否是默认 1默认 0否

    @Column(name = "alias")
	private String alias;//别名

}
