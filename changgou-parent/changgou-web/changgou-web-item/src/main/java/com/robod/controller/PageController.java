package com.robod.controller;

import com.robod.entity.Result;
import com.robod.entity.StatusCode;
import com.robod.service.intf.PageService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Robod
 * @date 2020/7/30 22:35
 */
@RestController
@RequestMapping("/page")
public class PageController {

    private final PageService pageService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    /**
     * 生成静态页面
     *
     * @param id
     * @return
     */
    @RequestMapping("/createHtml/{id}")
    public Result createHtml(@PathVariable(name = "id") Long id) {
        pageService.createPageHtml(id);
        return new Result(true, StatusCode.OK, "ok");
    }

    /**
     * 删除静态页面
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteHtml/{id}")
    public Result deleteHtml(@PathVariable(name = "id") Long id) {
        pageService.deletePageHtml(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }
}
