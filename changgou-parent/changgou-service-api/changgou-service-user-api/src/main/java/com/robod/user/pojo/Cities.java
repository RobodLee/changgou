package com.robod.user.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/****
 * @Author:admin
 * @Description:Cities构建
 * @Date 2019/6/14 19:13
 *****/
@Data
@Table(name="tb_cities")
public class Cities implements Serializable{

	@Id
    @Column(name = "city_id")
	private String cityId;//城市ID

    @Column(name = "city")
	private String city;//城市名称

    @Column(name = "province_id")
	private String provinceId;//省份ID

}
