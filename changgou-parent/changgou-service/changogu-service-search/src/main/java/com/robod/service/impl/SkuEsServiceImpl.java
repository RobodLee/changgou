package com.robod.service.impl;

import com.alibaba.fastjson.JSON;
import com.robod.entity.SkuInfo;
import com.robod.goods.feign.SkuFeign;
import com.robod.goods.pojo.Sku;
import com.robod.mapper.SkuEsMapper;
import com.robod.service.intf.SkuEsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Robod
 * @date 2020/7/19 18:17
 */
@Service("skuEsService")
public class SkuEsServiceImpl implements SkuEsService {

    private final SkuEsMapper skuEsMapper;
    private final SkuFeign skuFeign;

    public SkuEsServiceImpl(SkuEsMapper skuEsMapper, SkuFeign skuFeign) {
        this.skuEsMapper = skuEsMapper;
        this.skuFeign = skuFeign;
    }

    @Override
    public void importData() {
        List<Sku> skuList = skuFeign.findAll().getData();
        List<SkuInfo> skuInfos = JSON.parseArray(JSON.toJSONString(skuList), SkuInfo.class);
        //将spec字符串转化成map，map的key会自动生成Field
        for (SkuInfo skuInfo : skuInfos) {
            Map<String,Object> map = JSON.parseObject(skuInfo.getSpec(),Map.class);
            skuInfo.setSpecMap(map);
        }
        skuEsMapper.saveAll(skuInfos);
    }

}
