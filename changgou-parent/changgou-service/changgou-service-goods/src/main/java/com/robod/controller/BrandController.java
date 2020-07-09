package com.robod.controller;

import com.github.pagehelper.PageInfo;
import com.robod.entity.Result;
import com.robod.entity.StatusCode;
import com.robod.goods.pojo.Brand;
import com.robod.service.intf.BrandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Robod
 * @date 2020/7/2 9:00
 */
@RestController
@RequestMapping("/brand")
@CrossOrigin
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    /**
     * 查询所有的品牌信息
     *
     * @return
     */
    @GetMapping
    public Result<List<Brand>> findAll() {
        List<Brand> brands = brandService.findAll();
        return new Result<>(true, StatusCode.OK, "查询成功", brands);
    }

    /**
     * 根据id查询品牌信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Brand> findById(@PathVariable("id") Integer id) {
        Brand brand = brandService.findById(id);
        return new Result<Brand>(true, StatusCode.OK, "查询成功", brand);
    }

    /**
     * 添加品牌
     *
     * @param brand
     */
    @PostMapping
    public Result add(@RequestBody Brand brand) {
        System.out.println(brand.toString());
        brandService.add(brand);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 根据id修改品牌信息
     *
     * @param id
     * @param brand
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@PathVariable("id") Integer id, @RequestBody Brand brand) {
        brand.setId(id);
        brandService.update(brand);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 根据id删除品牌
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        brandService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 条件查询
     *
     * @param brand
     * @return
     */
    @PostMapping("/search")
    public Result<List<Brand>> findList(@RequestBody(required = false) Brand brand) {
        List<Brand> brands = brandService.findList(brand);
        return new Result<>(true, StatusCode.OK, "条件查询成功", brands);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo<Brand>> findPage(@PathVariable("page") int page, @PathVariable("size") int size) {
        PageInfo<Brand> brandPageInfo = brandService.findPage(page, size);
        return new Result<>(true, StatusCode.OK, "条件查询成功", brandPageInfo);
    }

    /**
     * 分页+条件查询
     * @param brand
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo<Brand>> findPage
            (@RequestBody(required = false) Brand brand, @PathVariable("page") int page, @PathVariable("size") int size) {
        PageInfo<Brand> brandPageInfo = brandService.findPage(brand,page, size);
        return new Result<>(true, StatusCode.OK, "条件查询成功", brandPageInfo);
    }

    /**
     * 根据分类id查询对应的品牌集合
     * @param categoryId
     * @return
     * Controller层 BrandController.java
     */
    @GetMapping("/category/{id}")
    public Result<List<Brand>> findByCategory(@PathVariable("id") int categoryId) {
        List<Brand> brands = brandService.findByCategory(categoryId);
        return new Result<>(true,StatusCode.OK,"查询成功",brands);
    }
}
