package com.robod.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Robod
 * @date 2020/7/19 18:07
 */
@Data
@Document(indexName = "sku_info", type = "docs")
public class SkuInfo implements Serializable {

    @Id
    private Long id;//商品id，同时也是商品编号

    /**
     * SKU名称
     * FieldType.Text支持分词
     * analyzer 创建索引的分词器
     * searchAnalyzer 搜索时使用的分词器
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String name;

    @Field(type = FieldType.Double)
    private Long price;//商品价格，单位为：元

    private Integer num;//库存数量

    private String image;//商品图片

    private String status;//商品状态，1-正常，2-下架，3-删除

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss || yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;//创建时间

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss || yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;//更新时间

    private String isDefault; //是否默认

    private Long spuId;//SPU_ID

    private Long categoryId;//类目ID

    @Field(type = FieldType.Keyword)
    private String categoryName;//类目名称,不分词

    @Field(type = FieldType.Keyword)
    private String brandName;//品牌名称，不分词

    private String spec;//规格

    private Map<String, Object> specMap;//规格参数

}