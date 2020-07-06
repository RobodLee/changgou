package com.robod.goods.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/****
 * @author robod
 *****/
@Data
public class Pref implements Serializable {

    //ID
    private Integer id;
    //分类ID
    private Integer cateId;
    //消费金额
    private Integer buyMoney;
    //优惠金额
    private Integer preMoney;
    //活动开始日期
    private Date startTime;
    //活动截至日期
    private Date endTime;
    //类型,1:普通订单，2：限时活动
    private String type;
    //状态,1:有效，0：无效
    private String state;

}
