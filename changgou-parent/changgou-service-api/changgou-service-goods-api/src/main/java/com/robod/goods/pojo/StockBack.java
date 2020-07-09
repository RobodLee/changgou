package com.robod.goods.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/****
 * @author robod
 *****/
@Data
@Table(name="tb_stock_back")
public class StockBack implements Serializable{

    @Column(name = "order_id")
    private String orderId;//订单id

    @Id
    @Column(name = "sku_id")
    private String skuId;//SKU的id

    @Column(name = "num")
    private Integer num;//回滚数量

    @Column(name = "status")
    private String status;//回滚状态

    @Column(name = "create_time")
    private Date createTime;//创建时间

    @Column(name = "back_time")
    private Date backTime;//回滚时间

}
