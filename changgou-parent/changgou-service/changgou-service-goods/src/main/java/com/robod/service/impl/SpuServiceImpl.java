package com.robod.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.robod.entity.IdWorker;
import com.robod.goods.pojo.*;
import com.robod.mapper.BrandMapper;
import com.robod.mapper.CategoryMapper;
import com.robod.mapper.SkuMapper;
import com.robod.mapper.SpuMapper;
import com.robod.service.intf.SpuService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/****
 * @Author:admin
 * @Description:Spu业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service("spuService")
public class SpuServiceImpl implements SpuService {

    private final SpuMapper spuMapper;

    private final IdWorker idWorker;

    private final SkuMapper skuMapper;

    private final CategoryMapper categoryMapper;

    private final BrandMapper brandMapper;

    public SpuServiceImpl(SpuMapper spuMapper, IdWorker idWorker, SkuMapper skuMapper, CategoryMapper categoryMapper, BrandMapper brandMapper) {
        this.spuMapper = spuMapper;
        this.idWorker = idWorker;
        this.skuMapper = skuMapper;
        this.categoryMapper = categoryMapper;
        this.brandMapper = brandMapper;
    }


    /**
     * Spu条件+分页查询
     *
     * @param spu  查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Spu> findPage(Spu spu, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //搜索条件构建  排除掉 已删除的
        Example example = createExample(spu);
        //执行搜索
        return new PageInfo<Spu>(spuMapper.selectByExample(example));
    }

    /**
     * Spu分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Spu> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page, size);
        //分页查询
        return new PageInfo<Spu>(spuMapper.selectAll());
    }

    /**
     * Spu条件查询
     *
     * @param spu
     * @return
     */
    @Override
    public List<Spu> findList(Spu spu) {
        //构建查询条件
        Example example = createExample(spu);
        //根据构建的条件查询数据
        return spuMapper.selectByExample(example);
    }


    /**
     * Spu构建查询对象
     *
     * @param spu
     * @return
     */
    public Example createExample(Spu spu) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete", 0);//只找 没有被删除的
        if (spu != null) {
            // 主键
            if (!StringUtils.isEmpty(spu.getId())) {
                criteria.andEqualTo("id", spu.getId());
            }
            // 货号
            if (!StringUtils.isEmpty(spu.getSn())) {
                criteria.andEqualTo("sn", spu.getSn());
            }
            // SPU名
            if (!StringUtils.isEmpty(spu.getName())) {
                criteria.andLike("name", "%" + spu.getName() + "%");
            }
            // 副标题
            if (!StringUtils.isEmpty(spu.getCaption())) {
                criteria.andEqualTo("caption", spu.getCaption());
            }
            // 品牌ID
            if (!StringUtils.isEmpty(spu.getBrandId())) {
                criteria.andEqualTo("brandId", spu.getBrandId());
            }
            // 一级分类
            if (!StringUtils.isEmpty(spu.getCategory1Id())) {
                criteria.andEqualTo("category1Id", spu.getCategory1Id());
            }
            // 二级分类
            if (!StringUtils.isEmpty(spu.getCategory2Id())) {
                criteria.andEqualTo("category2Id", spu.getCategory2Id());
            }
            // 三级分类
            if (!StringUtils.isEmpty(spu.getCategory3Id())) {
                criteria.andEqualTo("category3Id", spu.getCategory3Id());
            }
            // 模板ID
            if (!StringUtils.isEmpty(spu.getTemplateId())) {
                criteria.andEqualTo("templateId", spu.getTemplateId());
            }
            // 运费模板id
            if (!StringUtils.isEmpty(spu.getFreightId())) {
                criteria.andEqualTo("freightId", spu.getFreightId());
            }
            // 图片
            if (!StringUtils.isEmpty(spu.getImage())) {
                criteria.andEqualTo("image", spu.getImage());
            }
            // 图片列表
            if (!StringUtils.isEmpty(spu.getImages())) {
                criteria.andEqualTo("images", spu.getImages());
            }
            // 售后服务
            if (!StringUtils.isEmpty(spu.getSaleService())) {
                criteria.andEqualTo("saleService", spu.getSaleService());
            }
            // 介绍
            if (!StringUtils.isEmpty(spu.getIntroduction())) {
                criteria.andEqualTo("introduction", spu.getIntroduction());
            }
            // 规格列表
            if (!StringUtils.isEmpty(spu.getSpecItems())) {
                criteria.andEqualTo("specItems", spu.getSpecItems());
            }
            // 参数列表
            if (!StringUtils.isEmpty(spu.getParaItems())) {
                criteria.andEqualTo("paraItems", spu.getParaItems());
            }
            // 销量
            if (!StringUtils.isEmpty(spu.getSaleNum())) {
                criteria.andEqualTo("saleNum", spu.getSaleNum());
            }
            // 评论数
            if (!StringUtils.isEmpty(spu.getCommentNum())) {
                criteria.andEqualTo("commentNum", spu.getCommentNum());
            }
            // 是否上架
            if (!StringUtils.isEmpty(spu.getIsMarketable())) {
                criteria.andEqualTo("isMarketable", spu.getIsMarketable());
            }
            // 是否启用规格
            if (!StringUtils.isEmpty(spu.getIsEnableSpec())) {
                criteria.andEqualTo("isEnableSpec", spu.getIsEnableSpec());
            }
            // 是否删除
            if (!StringUtils.isEmpty(spu.getIsDelete())) {
                criteria.andEqualTo("isDelete", spu.getIsDelete());
            }
            // 审核状态
            if (!StringUtils.isEmpty(spu.getStatus())) {
                criteria.andEqualTo("status", spu.getStatus());
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
    public void delete(Long id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if (spu == null) {
            throw new RuntimeException("商品不存在");
        }
        if (!spu.getIsDelete().equals("1")) {
            throw new RuntimeException("必须先逻辑删除");
        }
        spuMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Spu
     *
     * @param spu
     */
    @Override
    public void update(Spu spu) {
        spuMapper.updateByPrimaryKey(spu);
    }

    /**
     * 增加Spu
     *
     * @param spu
     */
    @Override
    public void add(Spu spu) {
        spuMapper.insert(spu);
    }

    /**
     * 根据ID查询Spu
     *
     * @param id
     * @return
     */
    @Override
    public Spu findById(Long id) {
        return spuMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Spu全部数据
     *
     * @return
     */
    @Override
    public List<Spu> findAll() {
        return spuMapper.selectAll();
    }

    @Override
    public void save(Goods goods) {
        Spu spu = goods.getSpu();
        if (spu.getId() == null) {
            //新增spu
            spu.setId(idWorker.nextId());
            spuMapper.insertSelective(spu);
        } else {
            //修改spu并删除sku
            spuMapper.updateByPrimaryKeySelective(spu);
            Sku sku = new Sku();
            sku.setSpuId(spu.getId());
            skuMapper.delete(sku);
        }

        Category category = categoryMapper.findById(spu.getCategory3Id());
        Brand brand = brandMapper.findById(spu.getBrandId());

        //新增sku
        List<Sku> skuList = goods.getSkuList();
        LocalDateTime time = LocalDateTime.now();
        for (Sku sku : skuList) {
            StringBuilder name = new StringBuilder(spu.getName());
            if (sku.getSpec() != null) {
                Map<String, String> keyMap = JSONObject.parseObject(sku.getSpec(), Map.class);
                for (String spec : keyMap.keySet()) {
                    name.append(spec);
                }
            }
            sku.setId(idWorker.nextId());
            sku.setName(name.toString());
            sku.setCreateTime(time);
            sku.setUpdateTime(time);
            sku.setSpuId(spu.getId());
            sku.setCategoryId(category.getId());
            sku.setCategoryName(category.getName());
            sku.setBrandName(brand.getName());
            skuMapper.insertSelective(sku);
        }
    }

    @Override
    public Goods findGoodsById(long id) {
        Goods goods = new Goods();
        Spu spu = spuMapper.findById(id);
        goods.setSpu(spu);
        List<Sku> skuList = skuMapper.findBySpuId(spu.getId());
        goods.setSkuList(skuList);
        return goods;
    }

    @Override
    public void auditSpu(long id) {
        if (spuMapper.audit(id) == 0) {
            throw new RuntimeException("审核失败");
        }
    }

    @Override
    public void putSpu(long id) {
        if (spuMapper.putSpu(id) == 0) {//已经被删除了 或者商品部存在
            throw new RuntimeException("上架失败");
        }
    }

    @Override
    public int putMany(long[] ids) {
        int num = spuMapper.putMany(ids);
        if (num == 0) {
            throw new RuntimeException("上架失败");
        }
        return num;
    }

    @Override
    public void pullSpu(long id) {
        if (spuMapper.pullSpu(id) == 0) {//已经被删除了 或者商品部存在
            throw new RuntimeException("下架失败");
        }
    }

    @Override
    public int pullMany(long[] ids) {
        int num = spuMapper.pullMany(ids);
        if (num == 0) {
            throw new RuntimeException("下架失败");
        }
        return num;
    }

    @Override
    public void logicDeleteSpu(Long id) {
        // update set is_delete=1 where id =? and is_delete=0
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if (spu == null) {
            throw new RuntimeException("商品不存在");
        }

        if (spu.getIsMarketable().equals("1")) {
            throw new RuntimeException("商品还没下架,不能删除");
        }
        spu.setIsDelete("1");
        spu.setStatus("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public void restoreSpu(Long id) {
        // update set is_delete=0 where id =? and is_delete=1
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if (spu == null) {
            throw new RuntimeException("商品不存在");
        }
        Spu data = new Spu();
        data.setIsDelete("0");//恢复
        Example exmaple = new Example(Spu.class);
        Example.Criteria criteria = exmaple.createCriteria();
        criteria.andEqualTo("id", id);//where id =1
        criteria.andEqualTo("isDelete", "1");
        spuMapper.updateByExampleSelective(data, exmaple);
// spuMapper.updateByPrimaryKeySelective(spu);//根据主键来进行更新  update set name=? where id=?
    }


}
