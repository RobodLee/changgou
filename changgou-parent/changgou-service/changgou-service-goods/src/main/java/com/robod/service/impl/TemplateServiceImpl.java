package com.robod.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.robod.goods.pojo.Template;
import com.robod.mapper.TemplateMapper;
import com.robod.service.intf.TemplateService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author Robod
 * @date 2020/7/7 18:56
 */
@Service("templateService")
public class TemplateServiceImpl implements TemplateService {

    private final TemplateMapper templateMapper;

    public TemplateServiceImpl(TemplateMapper templateMapper) {
        this.templateMapper = templateMapper;
    }

    /**
     * Template条件+分页查询
     *
     * @param template 查询条件
     * @param page     页码
     * @param size     页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Template> findPage(Template template, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //搜索条件构建
        Example example = createExample(template);
        //执行搜索
        return new PageInfo<Template>(templateMapper.selectByExample(example));
    }

    /**
     * Template分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Template> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page, size);
        //分页查询
        return new PageInfo<Template>(templateMapper.selectAll());
    }

    /**
     * Template条件查询
     *
     * @param template
     * @return
     */
    @Override
    public List<Template> findList(Template template) {
        //构建查询条件
        Example example = createExample(template);
        //根据构建的条件查询数据
        return templateMapper.selectByExample(example);
    }


    /**
     * Template构建查询对象
     *
     * @param template
     * @return
     */
    public Example createExample(Template template) {
        Example example = new Example(Template.class);
        Example.Criteria criteria = example.createCriteria();
        if (template != null) {
            // ID
            if (template.getId() != null) {
                criteria.andEqualTo("id", template.getId());
            }
            // 模板名称
            if (!StringUtils.isEmpty(template.getName())) {
                criteria.andLike("name", "%" + template.getName() + "%");
            }
            // 规格数量
            if (template.getSpecNum() != null) {
                criteria.andEqualTo("specNum", template.getSpecNum());
            }
            // 参数数量
            if (template.getParaNum() != null) {
                criteria.andEqualTo("paraNum", template.getParaNum());
            }
        }
        return example;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        templateMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Template
     *
     * @param template
     */
    @Override
    public void update(Template template) {
        templateMapper.updateByPrimaryKey(template);
    }

    /**
     * 增加Template
     *
     * @param template
     */
    @Override
    public void add(Template template) {
        templateMapper.insert(template);
    }

    /**
     * 根据ID查询Template
     *
     * @param id
     * @return
     */
    @Override
    public Template findById(Integer id) {
        return templateMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Template全部数据
     *
     * @return
     */
    @Override
    public List<Template> findAll() {
        return templateMapper.selectAll();
    }
}