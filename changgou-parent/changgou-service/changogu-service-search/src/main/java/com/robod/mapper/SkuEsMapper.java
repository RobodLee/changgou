package com.robod.mapper;

import com.robod.entity.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Robod
 * @date 2020/7/19 18:20
 */
public interface SkuEsMapper extends ElasticsearchRepository<SkuInfo,Long> {
}
