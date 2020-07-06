package com.robod.goods.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author robod
 */
@Data
public class Album implements Serializable{

	//编号
	private Long id;
	//相册名称
	private String title;
	//相册封面
	private String image;
	//图片列表
	private String imageItems;

}
