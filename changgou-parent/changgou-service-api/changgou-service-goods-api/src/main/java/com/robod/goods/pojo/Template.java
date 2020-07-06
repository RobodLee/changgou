package com.robod.goods.pojo;

import lombok.Data;

import java.io.Serializable;

/****
 * @author robod
 *****/
@Data
public class Template implements Serializable {

    //ID
    private Integer id;
    //模板名称
    private String name;
    //规格数量
    private Integer specNum;
    //参数数量
    private Integer paraNum;

}
