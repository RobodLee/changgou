package com.robod.service.intf;

/**
 * @author Robod
 * @date 2020/7/30 22:36
 */
public interface PageService {
    /**
     * 根据商品的ID 生成静态页
     *
     * @param spuId
     */
    void createPageHtml(Long spuId);

    void deletePageHtml(Long id);
}
