package com.robod.goods.pojo;


import lombok.Data;

import java.io.Serializable;

/****
 * @author robod
 *****/
@Data
public class Spec implements Serializable {

    //ID
    private Integer id;
    //名称
    private String name;
    //规格选项
    private String options;
    //排序
    private Integer seq;
    //模板ID
    private Integer templateId;

}
