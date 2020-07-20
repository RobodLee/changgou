package com.robod.controller;

import com.robod.entity.Result;
import com.robod.entity.SearchEntity;
import com.robod.entity.StatusCode;
import com.robod.service.intf.SkuEsService;
import org.springframework.web.bind.annotation.*;

/**
 * @author Robod
 * @date 2020/7/19 18:16
 */
@RestController
@RequestMapping("/search")
@CrossOrigin
public class SkuEsController {

    private final SkuEsService skuEsService;

    public SkuEsController(SkuEsService skuEsService) {
        this.skuEsService = skuEsService;
    }

    /**
     * 数据导入ES
     * @return
     */
    @GetMapping("/import")
    public Result importData(){
        skuEsService.importData();
        return new Result(true, StatusCode.OK,"数据导入成功");
    }

    /**
     * 根据关键词搜索
     * @param keywords
     * @return
     */
    @GetMapping
    public Result<SearchEntity> searchByKeywords(@RequestParam(required = false)String keywords) {
        SearchEntity searchEntity = skuEsService.searchByKeywords(keywords);
        return new Result<>(true,StatusCode.OK,"根据关键词搜索成功",searchEntity);
    }
}
