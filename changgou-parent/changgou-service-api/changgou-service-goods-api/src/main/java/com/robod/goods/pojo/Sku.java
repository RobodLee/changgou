package com.robod.goods.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/****
 * @author robod
 *****/
@Data
public class Sku implements Serializable {

    //商品id
    private Long id;
    //商品条码
    private String sn;
    //SKU名称
    private String name;
    //价格（分）
    private Integer price;
    //库存数量
    private Integer num;
    //库存预警数量
    private Integer alertNum;
    //商品图片
    private String image;
    //商品图片列表
    private String images;
    //重量（克）
    private Integer weight;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //SPUID
    private Long spuId;
    //类目ID
    private Integer categoryId;
    //类目名称
    private String categoryName;
    //品牌名称
    private String brandName;
    //规格
    private String spec;
    //销量
    private Integer saleNum;
    //评论数
    private Integer commentNum;
    //商品状态 1-正常，2-下架，3-删除
    private String status;

}
