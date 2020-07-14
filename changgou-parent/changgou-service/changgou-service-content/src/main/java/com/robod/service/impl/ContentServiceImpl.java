package com.robod.service.impl;

import com.robod.content.pojo.Content;
import com.robod.mapper.ContentMapper;
import com.robod.service.intf.ContentService;
import org.springframework.stereotype.Service;

import java.util.List;

/****
 * @author robod
 * @Description:Content业务层接口实现类
 *****/
@Service("contentService")
public class ContentServiceImpl implements ContentService {

    private final ContentMapper contentMapper;

    public ContentServiceImpl(ContentMapper contentMapper) {
        this.contentMapper = contentMapper;
    }

    @Override
    public List<Content> findByCategoryId(long id) {
        return contentMapper.findByCategoryId(id);
    }
}
