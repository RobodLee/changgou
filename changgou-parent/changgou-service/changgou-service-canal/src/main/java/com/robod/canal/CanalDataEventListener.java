package com.robod.canal;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.robod.content.feign.ContentFeign;
import com.robod.content.pojo.Content;
import com.robod.feign.SkuEsFeign;
import com.robod.goods.feign.SkuFeign;
import com.robod.goods.pojo.Sku;
import com.robod.item.feign.PageFeign;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * @author Robod
 * @date 2020/7/14 10:47
 * 实现MySQL数据监听
 */
@CanalEventListener
public class CanalDataEventListener {
    private final ContentFeign contentFeign;
    private final StringRedisTemplate stringRedisTemplate;
    private final PageFeign pageFeign;
    private final SkuFeign skuFeign;
    private final SkuEsFeign skuEsFeign;

    public CanalDataEventListener(ContentFeign contentFeign, StringRedisTemplate stringRedisTemplate,
                                  PageFeign pageFeign, SkuFeign skuFeign, SkuEsFeign skuEsFeign) {
        this.contentFeign = contentFeign;
        this.stringRedisTemplate = stringRedisTemplate;
        this.pageFeign = pageFeign;
        this.skuFeign = skuFeign;
        this.skuEsFeign = skuEsFeign;
    }

    /**
     * 监听数据变化，将数据写到Redis中
     * @param eventType
     * @param rowData
     */
    @ListenPoint(
            destination = "example",
            schema = "changgou_content",
            table = {"tb_content","tb_content_category"},
            eventType = {
                    CanalEntry.EventType.INSERT,
                    CanalEntry.EventType.UPDATE,
                    CanalEntry.EventType.DELETE}
    )
    public void onEventListener(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        String categoryId = getColumnValue(eventType,rowData);
        List<Content> contents = contentFeign.findByCategoryId(Long.parseLong(categoryId)).getData();
        stringRedisTemplate.boundValueOps("content_"+categoryId).set(JSON.toJSONString(contents));
    }

    private String getColumnValue(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        if (eventType == CanalEntry.EventType.UPDATE || eventType == CanalEntry.EventType.INSERT) {
            for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
                if ("category_id".equalsIgnoreCase(column.getName())) {
                    return column.getValue();
                }
            }
        }
        if (eventType == CanalEntry.EventType.DELETE) {
            for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
                if ("category_id".equalsIgnoreCase(column.getName())) {
                    return column.getValue();
                }
            }
        }
        return "";
    }

    @ListenPoint(destination = "example",
            schema = "changgou_goods",
            table = {"tb_spu"},
            eventType = {CanalEntry.EventType.UPDATE, CanalEntry.EventType.INSERT, CanalEntry.EventType.DELETE})
    public void onEventCustomSpu(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {

        //判断操作类型
        if (eventType == CanalEntry.EventType.DELETE) {
            String spuId = "";
            List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList();
            for (CanalEntry.Column column : beforeColumnsList) {
                if ("id".equals(column.getName())) {
                    spuId = column.getValue();//spuid
                    break;
                }
            }
            List<Sku> skuList = skuFeign.findBySpuId(Long.parseLong(spuId)).getData();
            skuFeign.deleteAllSkuBySpuId(Long.parseLong(spuId));
            pageFeign.deleteHtml(Long.parseLong(spuId));
            skuEsFeign.deleteList(skuList);
        }else{
            //新增 或者 更新
            List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
            String spuId = "";
            for (CanalEntry.Column column : afterColumnsList) {
                if (column.getName().equals("id")) {
                    spuId = column.getValue();
                    break;
                }
            }
            //更新 生成静态页
            pageFeign.createHtml(Long.valueOf(spuId));
        }
    }

}