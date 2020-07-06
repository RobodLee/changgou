package com.robod.goods.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/****
 * @author robod
 *****/
@Data
public class StockBack implements Serializable {

    //订单id
    private String orderId;
    //SKU的id
    private String skuId;
    //回滚数量
    private Integer num;
    //回滚状态
    private String status;
    //创建时间
    private Date createTime;
    //回滚时间
    private Date backTime;

}
