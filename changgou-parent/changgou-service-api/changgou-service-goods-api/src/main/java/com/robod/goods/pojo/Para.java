package com.robod.goods.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Robod
 **/
@Data
@Table(name="tb_para")
public class Para implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;//id

    @Column(name = "name")
    private String name;//名称

    @Column(name = "options")
    private String options;//选项

    @Column(name = "seq")
    private Integer seq;//排序

    @Column(name = "template_id")
    private Integer templateId;//模板ID

}
