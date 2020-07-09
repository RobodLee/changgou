package com.robod.service.intf;

import com.github.pagehelper.PageInfo;
import com.robod.goods.pojo.Brand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Robod
 * @date 2020/7/2 9:17
 */
public interface BrandService {

    /**
     * 查询所有的品牌信息
     *
     * @return
     */
    public List<Brand> findAll();

    /**
     * 根据id查询品牌信息
     *
     * @param id
     * @return
     */
    public Brand findById(Integer id);

    /**
     * 添加品牌
     *
     * @param brand
     */
    public void add(Brand brand);

    /**
     * 根据id修改品牌信息
     *
     * @param brand
     */
    public void update(@Param("brand") Brand brand);

    /**
     * 根据id删除品牌
     *
     * @param id
     */
    public void delete(Integer id);

    /**
     * 条件查询
     *
     * @param brand
     * @return
     */
    public List<Brand> findList(Brand brand);

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    public PageInfo<Brand> findPage(int page, int size);

    /**
     * 分页+条件搜索
     * @param brand
     * @param page
     * @param size
     * @return
     */
    public PageInfo<Brand> findPage(Brand brand , int page, int size);

    /**
     * 根据分类id查询对应的品牌集合
     * @param categoryId
     * @return
     */
    public List<Brand> findByCategory(int categoryId);
}
