package com.robod.mapper;
import com.robod.goods.pojo.Sku;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/****
 * @Author:admin
 * @Description:Sku的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("skuMapper")
public interface SkuMapper extends Mapper<Sku> {

    /**
     * 根据spu_id查找Sku
     * @param spuId
     * @return
     */
    @Select("select * from tb_sku where spu_id=#{spuId}")
    public List<Sku> findBySpuId(long spuId);

}
