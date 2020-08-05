package com.robod.user.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/****
 * @Author:admin
 * @Description:Areas构建
 * @Date 2019/6/14 19:13
 *****/
@Data
@Table(name="tb_areas")
public class Areas implements Serializable{

	@Id
    @Column(name = "area_id")
	private String areaId;//区域ID

    @Column(name = "area")
	private String area;//区域名称

    @Column(name = "city_id")
	private String cityId;//城市ID

}
