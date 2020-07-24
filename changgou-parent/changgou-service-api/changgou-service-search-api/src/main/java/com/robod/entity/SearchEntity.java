package com.robod.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Robod
 * @date 2020/7/20 8:38
 * 搜索功能前后端传参的格式
 */
@Data
public class SearchEntity {

    private String keywords;    //前端传过来的关键词信息

    private String brand;   //前端传过来的品牌信息

    private String category;    //前端传过来的分类信息

    private String pageNum;     //需要第几页的数据

    private String price;       //前端穿过来的价格区间字符串 300-500元   3000元以上

    private String sortField;   //指定要排序的域

    private String sortRule;    //指定要排序的方式 ASC/DESC

    private Map<String,String> searchSpec;  //前端传过来的规格信息

    private long total;     //搜索结果的总记录数

    private int totalPages; //查询结果的总页数

    private List<String> categoryList;  //分类集合

    private List<String> brandList;     //品牌集合

    private Map<String, Set<String>> specMap; //规格集合

    private List<SkuInfo> rows; //搜索结果的集合

    public SearchEntity() {
    }

    public SearchEntity(long total, int totalPages, List<String> categoryList,
                        List<String> brandList, Map<String, Set<String>> specMap, List<SkuInfo> rows) {
        this.total = total;
        this.totalPages = totalPages;
        this.categoryList = categoryList;
        this.brandList = brandList;
        this.specMap = specMap;
        this.rows = rows;
    }
}
