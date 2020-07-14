package com.robod.canal;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.robod.content.feign.ContentFeign;
import com.robod.content.pojo.Content;
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

    public CanalDataEventListener(ContentFeign contentFeign, StringRedisTemplate stringRedisTemplate) {
        this.contentFeign = contentFeign;
        this.stringRedisTemplate = stringRedisTemplate;
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

}