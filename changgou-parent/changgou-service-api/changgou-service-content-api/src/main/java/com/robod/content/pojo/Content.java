package com.robod.content.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/****
 * @author robod
 * @Description:Content构建
 *****/
@Data
@Table(name="tb_content")
public class Content implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;//

    @Column(name = "category_id")
	private Long categoryId;//内容类目ID

    @Column(name = "title")
	private String title;//内容标题

    @Column(name = "url")
	private String url;//链接

    @Column(name = "pic")
	private String pic;//图片绝对路径

    @Column(name = "status")
	private String status;//状态,0无效，1有效

    @Column(name = "sort_order")
	private Integer sortOrder;//排序

}
