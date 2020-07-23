package com.robod.service.intf;

import com.robod.entity.SearchEntity;

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
}
