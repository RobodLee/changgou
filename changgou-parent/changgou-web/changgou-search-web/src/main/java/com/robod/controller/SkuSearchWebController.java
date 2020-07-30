package com.robod.controller;

import com.robod.entity.SearchEntity;
import com.robod.feign.SkuEsFeign;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
    public String searchByKeywords(SearchEntity searchEntity
            , Model model, HttpServletRequest request) {
        SearchEntity result = skuFeign.searchByKeywords(searchEntity).getData();
        result.setUrl(getUrl(request)[0]);
        result.setSortUrl(getUrl(request)[1]);
        model.addAttribute("result", result);
        return "search";
    }

    private String[] getUrl(HttpServletRequest request) {
        StringBuilder url = new StringBuilder("/search/list");
        StringBuilder sortUrl = new StringBuilder("/search/list");
        Map<String, String[]> parameters = request.getParameterMap();
        if (parameters!=null&&parameters.size()>0){
            url.append("?");
            sortUrl.append("?");
            if (!parameters.containsKey("keywords")) {
                url.append("keywords=小米&");
                sortUrl.append("keywords=小米&");
            }
            for (String key:parameters.keySet()){
                if ("searchPage".equalsIgnoreCase(key)){
                    continue;
                }
                url.append(key).append("=").append(parameters.get(key)[0]).append("&");
                if (!("sortField".equalsIgnoreCase(key)||"sortRule".equalsIgnoreCase(key))){
                    sortUrl.append(key).append("=").append(parameters.get(key)[0]).append("&");
                }
            }
            url.deleteCharAt(url.length()-1);
            sortUrl.deleteCharAt(sortUrl.length()-1);
        }
        return new String[]{url.toString().replace(" ","+"),
                sortUrl.toString().replace(" ","+")};
    }

}
