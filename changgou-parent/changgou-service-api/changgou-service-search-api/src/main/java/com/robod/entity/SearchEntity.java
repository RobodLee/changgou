package com.robod.entity;

import lombok.Data;

import java.util.List;

/**
 * @author Robod
 * @date 2020/7/20 8:38
 * 搜索功能前后端传参的格式
 */
@Data
public class SearchEntity {

    private long total;     //搜索结果的总记录数

    private int totalPages; //查询结果的总页数

    private List<String> categoryList;  //分类集合

    private List<SkuInfo> rows; //搜索结果的集合

    public SearchEntity() {
    }

    public SearchEntity(long total, int totalPages, List<String> categoryList, List<SkuInfo> rows) {
        this.total = total;
        this.totalPages = totalPages;
        this.categoryList = categoryList;
        this.rows = rows;
    }
}
