package com.robod.goods.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Robod
 */
@Data
@Table(name="tb_category")
public class Category implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;//分类ID

    @Column(name = "name")
    private String name;//分类名称

    @Column(name = "goods_num")
    private Integer goodsNum;//商品数量

    @Column(name = "is_show")
    private String isShow;//是否显示

    @Column(name = "is_menu")
    private String isMenu;//是否导航

    @Column(name = "seq")
    private Integer seq;//排序

    @Column(name = "parent_id")
    private Integer parentId;//上级ID

    @Column(name = "template_id")
    private Integer templateId;//模板ID

}