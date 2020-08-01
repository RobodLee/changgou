package com.robod.goods.feign;

import com.robod.entity.Result;
import com.robod.goods.pojo.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Robod
 * @date 2020/7/30 22:29
 */
@FeignClient(name = "goods")
@RequestMapping("/category")
public interface CategoryFeign {

    /***
     * 根据ID查询Category数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Category> findById(@PathVariable Integer id);
}
