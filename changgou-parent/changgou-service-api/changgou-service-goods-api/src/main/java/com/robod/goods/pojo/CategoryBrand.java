package com.robod.goods.pojo;

import lombok.Data;

import java.io.Serializable;

/****
 * @author robod
 *****/
@Data
public class CategoryBrand implements Serializable {

    //分类ID
    private Integer categoryId;
    //品牌ID
    private Integer brandId;

}
