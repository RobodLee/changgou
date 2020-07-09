package com.robod.goods.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/****
 * @author robod
 *****/
@Data
@Table(name="tb_category_brand")
public class CategoryBrand implements Serializable{

    @Id
    @Column(name = "category_id")
    private Integer categoryId;//分类ID

    @Column(name = "brand_id")
    private Integer brandId;//品牌ID

}
