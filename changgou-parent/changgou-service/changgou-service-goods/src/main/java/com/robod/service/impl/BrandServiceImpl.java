package com.robod.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.robod.goods.pojo.Brand;
import com.robod.mapper.BrandMapper;
import com.robod.service.intf.BrandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Robod
 * @date 2020/7/2 9:17
 */
@Service("brandService")
@Transactional(rollbackFor = Exception.class)
public class BrandServiceImpl implements BrandService {

    private final BrandMapper brandMapper;

    public BrandServiceImpl(BrandMapper brandMapper) {
        this.brandMapper = brandMapper;
    }

    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }

    @Override
    public Brand findById(Integer id) {
        return brandMapper.findById(id);
    }

    @Override
    public void add(Brand brand) {
        brandMapper.add(brand);
    }

    @Override
    public void update(Brand brand) {
        brandMapper.update(brand);
    }

    @Override
    public void delete(Integer id) {
        brandMapper.deleteBrand(id);
    }

    @Override
    public List<Brand> findList(Brand brand) {
        return brandMapper.findList(brand);
    }

    @Override
    public PageInfo<Brand> findPage(int page, int size) {
        PageHelper.startPage(page,size);
        List<Brand> brands = brandMapper.findAll();
        return new PageInfo<>(brands);
    }

    @Override
    public PageInfo<Brand> findPage(Brand brand, int page, int size) {
        PageHelper.startPage(page,size);
        List<Brand> brands = brandMapper.findList(brand);
        return new PageInfo<>(brands);
    }

    @Override
    public List<Brand> findByCategory(int categoryId) {
        return brandMapper.findByCategory(categoryId);
    }
}
