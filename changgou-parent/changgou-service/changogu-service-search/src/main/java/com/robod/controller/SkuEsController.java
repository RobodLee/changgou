package com.robod.controller;

import com.robod.entity.Result;
import com.robod.entity.StatusCode;
import com.robod.service.intf.SkuEsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
