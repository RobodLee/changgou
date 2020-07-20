package com.robod.service.impl;

import com.alibaba.fastjson.JSON;
import com.robod.entity.SearchEntity;
import com.robod.entity.SkuInfo;
import com.robod.goods.feign.SkuFeign;
import com.robod.goods.pojo.Sku;
import com.robod.mapper.SkuEsMapper;
import com.robod.service.intf.SkuEsService;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
    private final ElasticsearchTemplate elasticsearchTemplate;

    public SkuEsServiceImpl(SkuEsMapper skuEsMapper, SkuFeign skuFeign, ElasticsearchTemplate elasticsearchTemplate) {
        this.skuEsMapper = skuEsMapper;
        this.skuFeign = skuFeign;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public void importData() {
        List<Sku> skuList = skuFeign.findAll().getData();
        List<SkuInfo> skuInfos = JSON.parseArray(JSON.toJSONString(skuList), SkuInfo.class).subList(0,1000);
        //将spec字符串转化成map，map的key会自动生成Field
        for (SkuInfo skuInfo : skuInfos) {
            Map<String,Object> map = JSON.parseObject(skuInfo.getSpec(),Map.class);
            skuInfo.setSpecMap(map);
        }
        skuEsMapper.saveAll(skuInfos);
    }

    @Override
    public SearchEntity searchByKeywords(String keywords) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        if (!StringUtils.isEmpty(keywords)) {
            nativeSearchQueryBuilder.withQuery(QueryBuilders.queryStringQuery(keywords).field("name"));
            //terms: Create a new aggregation with the given name.
            nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("categories_grouping").field("categoryName"));
        }
        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();
        AggregatedPage<SkuInfo> skuInfos = elasticsearchTemplate.queryForPage(nativeSearchQuery, SkuInfo.class);
        StringTerms stringTerms = skuInfos.getAggregations().get("categories_grouping");
        List<String> categoryList = new ArrayList<>();
        for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
            categoryList.add(bucket.getKeyAsString());
        }
        return new SearchEntity(skuInfos.getTotalElements(),skuInfos.getTotalPages(),categoryList,skuInfos.getContent());
    }
}
