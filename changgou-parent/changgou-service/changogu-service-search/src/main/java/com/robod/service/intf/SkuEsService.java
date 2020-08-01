package com.robod.service.intf;

import com.robod.entity.SearchEntity;
import com.robod.goods.pojo.Sku;

import java.util.List;

/**
 * @author Robod
 * @date 2020/7/19 18:17
 */
public interface SkuEsService {

    /**
     * 导入数据到ES
     */
    void importData();

    /**
     * 根据关键词搜索
     * @param searchEntity
     * @return
     */
    SearchEntity searchByKeywords(SearchEntity searchEntity);

    /**
     * 删除Sku集合
     * @param list
     * @return
     */
    void deleteList(List<Sku> list);

    /**
     * 修改Sku集合
     * @param list
     * @return
     */
    void updateList(List<Sku> list);
}
