package com.robod.feign;

import com.robod.entity.Result;
import com.robod.entity.SearchEntity;
import com.robod.goods.pojo.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Robod
 * @date 2020/7/27 8:44
 */
@FeignClient(name="search")
@RequestMapping("/search")
public interface SkuEsFeign {

    /**
     * 搜索
     * @param searchEntity
     * @return
     */
    @GetMapping
    Result<SearchEntity> searchByKeywords(@RequestBody(required = false) SearchEntity searchEntity);

    /**
     * 删除Sku集合
     * @param list
     * @return
     */
    @DeleteMapping
    Result deleteList(@RequestBody List<Sku> list);

    /**
     * 修改Sku集合
     * @param list
     * @return
     */
    @PostMapping
    Result updateList(@RequestBody List<Sku> list);
}
