package com.robod.service.impl;

import com.alibaba.fastjson.JSON;
import com.robod.entity.SearchEntity;
import com.robod.entity.SkuInfo;
import com.robod.goods.feign.SkuFeign;
import com.robod.goods.pojo.Sku;
import com.robod.mapper.SkuEsMapper;
import com.robod.service.intf.SkuEsService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author Robod
 * @date 2020/7/19 18:17
 */
@Service("skuEsService")
public class SkuEsServiceImpl implements SkuEsService {

    private final SkuEsMapper skuEsMapper;
    private final SkuFeign skuFeign;
    private final ElasticsearchTemplate elasticsearchTemplate;

    private static final int SEARCH_PAGE_SIZE = 10;

    public SkuEsServiceImpl(SkuEsMapper skuEsMapper, SkuFeign skuFeign, ElasticsearchTemplate elasticsearchTemplate) {
        this.skuEsMapper = skuEsMapper;
        this.skuFeign = skuFeign;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public void importData() {
        List<Sku> skuList = skuFeign.findAll().getData();
        List<SkuInfo> skuInfos = JSON.parseArray(JSON.toJSONString(skuList), SkuInfo.class).subList(0, 1000);
        //将spec字符串转化成map，map的key会自动生成Field
        for (SkuInfo skuInfo : skuInfos) {
            Map<String, Object> map = JSON.parseObject(skuInfo.getSpec(), Map.class);
            skuInfo.setSpecMap(map);
        }
        skuEsMapper.saveAll(skuInfos);
    }

    @Override
    public SearchEntity searchByKeywords(SearchEntity searchEntity) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        HighlightBuilder.Field field = new HighlightBuilder.Field("name");  //指定域
        field.preTags("<em style=\"color:red;\">"); //指定前缀
        field.postTags("</em>");    //指定后缀
        field.fragmentSize(100);
        nativeSearchQueryBuilder.withHighlightFields(field);

        if (searchEntity != null && !StringUtils.isEmpty(searchEntity.getKeywords())) {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            nativeSearchQueryBuilder.withQuery(QueryBuilders.queryStringQuery(searchEntity.getKeywords()).field("name"));
            //terms: Create a new aggregation with the given name.
            if (!StringUtils.isEmpty(searchEntity.getCategory())) {
                boolQueryBuilder.filter(QueryBuilders.termQuery("categoryName", searchEntity.getCategory()));
            } else {
                nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms
                        ("categories_grouping").field("categoryName").size(10000));
            }
            if (!StringUtils.isEmpty(searchEntity.getBrand())) {
                boolQueryBuilder.filter(QueryBuilders.termQuery("brandName", searchEntity.getBrand()));
            } else {
                nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms
                        ("brands_grouping").field("brandName").size(10000));
            }
            if (!StringUtils.isEmpty(searchEntity.getPrice())) {
                String[] price = searchEntity.getPrice().replace("元", "")
                        .replace("以上", "").split("-");
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(Integer.parseInt(price[0])));
                if (price.length > 1) {
                    boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").lt(Integer.parseInt(price[1])));
                }
            }
            Map<String, String> searchSpec = searchEntity.getSearchSpec();
            if (searchSpec != null && searchSpec.size() > 0) {
                for (String key : searchSpec.keySet()) {
                    boolQueryBuilder.filter(QueryBuilders.termQuery("specMap." + key + ".keyword", searchSpec.get(key)));
                }
            }
            int pageNum = 1;
            if (!StringUtils.isEmpty(searchEntity.getPageNum())) {
                pageNum = Integer.parseInt(searchEntity.getPageNum());
            }
            nativeSearchQueryBuilder.withPageable(PageRequest.of(pageNum - 1, SEARCH_PAGE_SIZE));
            String sortField = searchEntity.getSortField();
            String sortRule = searchEntity.getSortRule();
            if (!StringUtils.isEmpty(sortField) && !StringUtils.isEmpty(sortRule)) {
                nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort(sortField).order(SortOrder.valueOf(sortRule)));
            }
            nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms
                    ("spec_grouping").field("spec.keyword").size(10000));
            nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
            NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();

            AggregatedPage<SkuInfo> skuInfos = elasticsearchTemplate
                    .queryForPage(nativeSearchQuery, SkuInfo.class, new SearchResultMapper() {
                        @Override
                        public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                            List<T> list = new ArrayList<>();
                            for (SearchHit hit : response.getHits()) {  //遍历所有数据
                                SkuInfo skuInfo = JSON.parseObject(hit.getSourceAsString(), SkuInfo.class);//非高亮数据
                                HighlightField highlightField = hit.getHighlightFields().get("name");      //高亮数据
                                if (highlightField != null && highlightField.getFragments() != null) {
                                    Text[] fragments = highlightField.getFragments();
                                    StringBuilder builder = new StringBuilder();
                                    for (Text fragment : fragments) {
                                        System.out.println("-----------------------");
                                        System.out.println(fragment.toString());
                                        builder.append(fragment.toString());
                                    }
                                    skuInfo.setName(builder.toString());
                                }
                                list.add((T) skuInfo);
                            }
                            return new AggregatedPageImpl<T>(list,pageable,response.getHits().getTotalHits());
                        }
                    });
//            AggregatedPage<SkuInfo> skuInfos = elasticsearchTemplate
//                    .queryForPage(nativeSearchQuery, SkuInfo.class);
            StringTerms categoryTerms = skuInfos.getAggregations()
                    .get("categories_grouping");
            StringTerms brandTerms = skuInfos.getAggregations()
                    .get("brands_grouping");
            StringTerms specTerms = skuInfos.getAggregations()
                    .get("spec_grouping");
            List<String> categoryList = new ArrayList<>();
            List<String> brandList = new ArrayList<>();
            Map<String, Set<String>> specMap = new HashMap<>(16);
            if (categoryTerms != null) {
                for (StringTerms.Bucket bucket : categoryTerms.getBuckets()) {
                    categoryList.add(bucket.getKeyAsString());
                }
            }
            if (brandTerms != null) {
                for (StringTerms.Bucket bucket : brandTerms.getBuckets()) {
                    brandList.add(bucket.getKeyAsString());
                }
            }
            for (StringTerms.Bucket bucket : specTerms.getBuckets()) {
                Map<String, String> map = JSON.parseObject(bucket.getKeyAsString(), Map.class);
                for (String key : map.keySet()) {
                    Set<String> specSet;
                    if (!specMap.containsKey(key)) {
                        specSet = new HashSet<>();
                        specMap.put(key, specSet);
                    } else {
                        specSet = specMap.get(key);
                    }
                    specSet.add(map.get(key));
                }
            }
            searchEntity.setTotal(skuInfos.getTotalElements());
            searchEntity.setTotalPages(skuInfos.getTotalPages());
            searchEntity.setCategoryList(categoryList);
            searchEntity.setBrandList(brandList);
            searchEntity.setSpecMap(specMap);
            searchEntity.setRows(skuInfos.getContent());
            return searchEntity;
        }
        return null;
    }

    public static List<String> receiveCollectionList(List<String> firstArrayList, List<String> secondArrayList) {
        List<String> resultList = new ArrayList<String>();
        LinkedList<String> result = new LinkedList<String>(firstArrayList);// 大集合用linkedlist
        HashSet<String> othHash = new HashSet<String>(secondArrayList);// 小集合用hashset
        Iterator<String> iter = result.iterator();// 采用Iterator迭代器进行数据的操作
        while (iter.hasNext()) {
            if (!othHash.contains(iter.next())) {
                iter.remove();
            }
        }
        resultList = new ArrayList<String>(result);
        return resultList;
    }

}
