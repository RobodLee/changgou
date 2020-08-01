package com.robod.goods.feign;

import com.robod.entity.Result;
import com.robod.goods.pojo.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
     *
     * @return
     */
    @GetMapping
    Result<List<Sku>> findAll();

    /***
     * 多条件搜索品牌数据
     * @param sku
     * @return
     */
    @PostMapping(value = "/search")
    Result<List<Sku>> findList(@RequestBody(required = false) Sku sku);

    /**
     * 根据spu_id查询对应的sku集合
     * @param id
     * @return
     */
    @GetMapping("/spu/{id}")
    Result<List<Sku>> findBySpuId(@PathVariable Long id);

    /**
     * 根据spuid删除对应的sku
     * @param id
     * @return
     */
    @DeleteMapping("/deleteAll/{id}")
    Result deleteAllSkuBySpuId(@PathVariable Long id);

}
