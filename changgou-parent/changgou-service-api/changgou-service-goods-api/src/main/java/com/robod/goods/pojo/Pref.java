package com.robod.goods.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/****
 * @author robod
 *****/
@Data
@Table(name="tb_pref")
public class Pref implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;//ID

    @Column(name = "cate_id")
    private Integer cateId;//分类ID

    @Column(name = "buy_money")
    private Integer buyMoney;//消费金额

    @Column(name = "pre_money")
    private Integer preMoney;//优惠金额

    @Column(name = "start_time")
    private Date startTime;//活动开始日期

    @Column(name = "end_time")
    private Date endTime;//活动截至日期

    @Column(name = "type")
    private String type;//类型,1:普通订单，2：限时活动

    @Column(name = "state")
    private String state;//状态,1:有效，0：无效

}
