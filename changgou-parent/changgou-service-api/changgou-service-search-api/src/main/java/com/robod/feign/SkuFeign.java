package com.robod.feign;

import com.robod.entity.SearchEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Robod
 * @date 2020/7/26 9:24
 */
@FeignClient(name="search")
@RequestMapping("/search")
public interface SkuFeign {

    /**
     * 搜索
     * @param searchEntity
     * @return
     */
    @GetMapping
    SearchEntity searchByKeywords(@RequestParam(required = false) SearchEntity searchEntity);
}
