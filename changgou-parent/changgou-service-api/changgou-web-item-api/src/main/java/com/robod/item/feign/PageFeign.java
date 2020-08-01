package com.robod.item.feign;

import com.robod.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Robod
 * @date 2020/7/31 22:58
 */
@FeignClient(name="item")
@RequestMapping("/page")
public interface PageFeign {

    /***
     * 根据SpuID生成静态页
     * @param id
     * @return
     */
    @RequestMapping("/createHtml/{id}")
    Result createHtml(@PathVariable(name="id") Long id);

    /**
     * 删除静态页面
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteHtml/{id}")
    public Result deleteHtml(@PathVariable(name = "id") Long id);
}
