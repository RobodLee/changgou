package com.robod.mapper.tk_mapper;

import com.robod.goods.pojo.Template;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Robod
 * @date 2020/7/7 18:12
 */
@Repository("templateMapper")
public interface TemplateMapper extends Mapper<Template> {
}
