package com.robod.goods.feign;

import com.robod.entity.Result;
import com.robod.goods.pojo.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Robod
 * @date 2020/7/19 18:00
 */
@FeignClient(name = "goods")
@RequestMapping("/sku")
public interface SkuFeign {

    /**
     * 查询所有的sku数据
     * @return
     */
    @GetMapping
    Result<List<Sku>> findAll();
}
