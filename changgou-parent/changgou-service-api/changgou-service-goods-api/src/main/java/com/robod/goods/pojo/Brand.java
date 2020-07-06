package com.robod.goods.pojo;

import lombok.Data;

import java.io.Serializable;

/****
 * @author Robod
 *****/
@Data
public class Brand implements Serializable {

	//品牌id
	private Integer id;
	//品牌名称
	private String name;
	//品牌图片地址
	private String image;
	//品牌的首字母
	private String letter;
	//排序
	private Integer seq;
}
