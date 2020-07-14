package com.robod.content.feign;

import com.robod.content.pojo.Content;
import com.robod.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Robod
 * @date 2020/7/14 17:40
 */
@FeignClient(name="content")
@RequestMapping(value = "/content")
public interface ContentFeign {

    /**
     * 根据分类ID查询所有广告
     * @param id
     * @return
     */
    @GetMapping(value = "/list/category/{id}")
    Result<List<Content>> findByCategoryId(@PathVariable long id);
}