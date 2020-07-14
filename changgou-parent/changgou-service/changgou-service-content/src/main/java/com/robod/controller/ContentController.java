package com.robod.controller;

import com.robod.content.pojo.Content;
import com.robod.entity.Result;
import com.robod.entity.StatusCode;
import com.robod.service.intf.ContentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author robod
 */
@RestController
@RequestMapping("/content")
@CrossOrigin
public class ContentController {

    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    /**
     * 根据分类的ID 获取该分类下的所有的广告的列表
     * @param id
     * @return
     */
    @GetMapping(value = "/list/category/{id}")
    public Result<List<Content>> findByCategoryId(@PathVariable long id){
       List<Content>  contents = contentService.findByCategoryId(id);
       return new Result<>(true,StatusCode.OK,"成功查询出所有的广告",contents);
    }

}
