package com.robod.goods.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Robod
 * 商品组合实体类
 */
@Data
public class Goods implements Serializable {
    //SPU
    private Spu spu;
    //SKU集合
    private List<Sku> skuList;

}