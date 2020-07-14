package com.robod.service.intf;

import com.robod.content.pojo.Content;

import java.util.List;

/****
 * @Author:admin
 * @Description:Content业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface ContentService {

    /**
     * 根据分类的ID 获取该分类下的所有的广告的列表
     * @param id
     * @return
     */
    List<Content> findByCategoryId(long id);

}
