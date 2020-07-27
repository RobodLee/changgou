package com.robod.feign;

import com.robod.entity.Result;
import com.robod.entity.SearchEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
