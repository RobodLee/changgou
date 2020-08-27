package com.robod.order.service.impl;

import com.robod.goods.feign.SkuFeign;
import com.robod.goods.feign.SpuFeign;
import com.robod.goods.pojo.Sku;
import com.robod.goods.pojo.Spu;
import com.robod.order.api.pojo.OrderItem;
import com.robod.order.service.intf.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Robod
 * @date 2020/8/22 11:04
 */
@Service("cartService")
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SpuFeign spuFeign;

    @Autowired
    private SkuFeign skuFeign;

    @Override
    public void add(long id, int num, String username) {
        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps("Cart_" + username);
        if (num <= 0){
            boundHashOperations.delete(id);
            Long size = boundHashOperations.size();
            if (size == null || size<=0) {
                redisTemplate.delete("Cart_" + username);
            }
            return;
        }
        Sku sku = skuFeign.findById(id).getData();
        if (sku == null) {
            throw new RuntimeException("未查询到商品信息");
        }
        Spu spu = spuFeign.findById(sku.getSpuId()).getData();
        if (spu == null) {
            throw new RuntimeException("数据库中数据异常");
        }
        OrderItem orderItem = createOrderItem(spu,sku,num);
        boundHashOperations.put(id,orderItem);
    }

    @Override
    public List<OrderItem> list(String username) {
        return (List<OrderItem>) redisTemplate.boundHashOps("Cart_" + username).values();
    }

    private OrderItem createOrderItem(Spu spu,Sku sku,int num) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCategoryId1(spu.getCategory1Id());
        orderItem.setCategoryId2(spu.getCategory2Id());
        orderItem.setCategoryId3(spu.getCategory3Id());
        orderItem.setSpuId(spu.getId());
        orderItem.setSkuId(sku.getId());
        orderItem.setName(sku.getName());
        orderItem.setNum(num);
        orderItem.setPrice(sku.getPrice());
        orderItem.setMoney(num * sku.getPrice());
        orderItem.setImage(spu.getImage());
        return orderItem;
    }
}
