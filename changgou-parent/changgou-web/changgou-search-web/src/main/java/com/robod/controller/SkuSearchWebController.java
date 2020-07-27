package com.robod.controller;

import com.robod.entity.SearchEntity;
import com.robod.feign.SkuEsFeign;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Robod
 * @date 2020/7/26 9:44
 */
@Controller
@RequestMapping("/search")
public class SkuSearchWebController {

    private final SkuEsFeign skuFeign;

    public SkuSearchWebController(SkuEsFeign skuFeign) {
        this.skuFeign = skuFeign;
    }

    @GetMapping("/list")
    public String searchByKeywords(@RequestBody(required = false) SearchEntity searchEntity
            , Model model) {
        SearchEntity result = skuFeign.searchByKeywords(searchEntity);
        model.addAttribute("result",result);
        return "search";
    }

}
