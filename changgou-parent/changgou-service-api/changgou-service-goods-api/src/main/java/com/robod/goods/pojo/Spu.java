package com.robod.goods.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/****
 * @author robod
 *****/
@Data
@Table(name="tb_spu")
public class Spu implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;//主键

    @Column(name = "sn")
    private String sn;//货号

    @Column(name = "name")
    private String name;//SPU名

    @Column(name = "caption")
    private String caption;//副标题

    @Column(name = "brand_id")
    private Integer brandId;//品牌ID

    @Column(name = "category1_id")
    private Integer category1Id;//一级分类

    @Column(name = "category2_id")
    private Integer category2Id;//二级分类

    @Column(name = "category3_id")
    private Integer category3Id;//三级分类

    @Column(name = "template_id")
    private Integer templateId;//模板ID

    @Column(name = "freight_id")
    private Integer freightId;//运费模板id

    @Column(name = "image")
    private String image;//图片

    @Column(name = "images")
    private String images;//图片列表

    @Column(name = "sale_service")
    private String saleService;//售后服务

    @Column(name = "introduction")
    private String introduction;//介绍

    @Column(name = "spec_items")
    private String specItems;//规格列表

    @Column(name = "para_items")
    private String paraItems;//参数列表

    @Column(name = "sale_num")
    private Integer saleNum;//销量

    @Column(name = "comment_num")
    private Integer commentNum;//评论数

    @Column(name = "is_marketable")
    private String isMarketable;//是否上架

    @Column(name = "is_enable_spec")
    private String isEnableSpec;//是否启用规格

    @Column(name = "is_delete")
    private String isDelete;//是否删除

    @Column(name = "status")
    private String status;//审核状态

}
