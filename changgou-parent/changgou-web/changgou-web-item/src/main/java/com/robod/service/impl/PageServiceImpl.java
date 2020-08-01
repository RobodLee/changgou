package com.robod.service.impl;

import com.alibaba.fastjson.JSON;
import com.robod.entity.Result;
import com.robod.goods.feign.CategoryFeign;
import com.robod.goods.feign.SkuFeign;
import com.robod.goods.feign.SpuFeign;
import com.robod.goods.pojo.Sku;
import com.robod.goods.pojo.Spu;
import com.robod.service.intf.PageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Robod
 * @date 2020/7/30 22:37
 */
@Service("pageService")
public class PageServiceImpl implements PageService {

    private final SpuFeign spuFeign;

    private final CategoryFeign categoryFeign;

    private final SkuFeign skuFeign;

    private final TemplateEngine templateEngine;

    @Value("${pagePath}")
    private String pagePath;    //生成静态文件路径

    public PageServiceImpl(SpuFeign spuFeign, CategoryFeign categoryFeign, SkuFeign skuFeign, TemplateEngine templateEngine) {
        this.spuFeign = spuFeign;
        this.categoryFeign = categoryFeign;
        this.skuFeign = skuFeign;
        this.templateEngine = templateEngine;
    }

    /***
     * 生成静态页
     * @param spuId
     */
    @Override
    public void createPageHtml(Long spuId) {
        // 1.上下文
        Context context = new Context();
        Map<String, Object> dataModel = buildDataModel(spuId);
        context.setVariables(dataModel);
        // 2.准备文件
        File dir = new File(pagePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File dest = new File(dir, spuId + ".html");
        // 3.生成页面
        try {
            PrintWriter writer = new PrintWriter(dest, "UTF-8");
            templateEngine.process("item", context, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePageHtml(Long id) {
        File htmlFile = new File(pagePath+"\\"+id+".html");
        if (htmlFile.exists()){
            htmlFile.delete();
        } else {
            throw new RuntimeException("文件不存在");
        }
    }

    /**
     * 构建数据模型
     *
     * @param spuId
     * @return
     */
    private Map<String, Object> buildDataModel(Long spuId) {
        //构建数据模型
        Map<String, Object> dataMap = new HashMap<>(16);
        //获取spu 和SKU列表
        Result<Spu> result = spuFeign.findById(spuId);
        Spu spu = result.getData();

        //获取分类信息
        dataMap.put("category1", categoryFeign.findById(spu.getCategory1Id()).getData());
        dataMap.put("category2", categoryFeign.findById(spu.getCategory2Id()).getData());
        dataMap.put("category3", categoryFeign.findById(spu.getCategory3Id()).getData());
        if (spu.getImages() != null) {
            dataMap.put("imageList", spu.getImages().split(","));
        }

        dataMap.put("specificationList", JSON.parseObject(spu.getSpecItems(), Map.class));
        dataMap.put("spu", spu);

        //根据spuId查询Sku集合
        Sku skuCondition = new Sku();
        skuCondition.setSpuId(spu.getId());
        Result<List<Sku>> resultSku = skuFeign.findList(skuCondition);
        dataMap.put("skuList", resultSku.getData());
        dataMap.put("sku",resultSku.getData().get(0));
        return dataMap;
    }
}
