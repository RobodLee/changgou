package com.robod.mapper;

import com.robod.goods.pojo.Spu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Arrays;

/****
 * @Author:admin
 * @Description:Spu的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("spuMapper")
public interface SpuMapper extends Mapper<Spu> {

    /**
     * 根据id查询spu
     *
     * @param id
     * @return
     */
    @Select("select * from tb_spu where id=#{id} limit 1")
    Spu findById(long id);

    /**
     * 审核商品
     *
     * @param id
     * @return
     */
    @Update("update tb_spu set status=1,is_marketable=1 where is_delete=0 and id = #{id}")
    int audit(long id);

    /**
     * 上架商品
     *
     * @param id
     * @return
     */
    @Update("update tb_spu set is_marketable=1 where id=#{id} and is_delete=0 and is_marketable=0 and status=1")
    int putSpu(long id);

    /**
     * 批量上架商品
     *
     * @param ids
     * @return
     */
    @UpdateProvider(type = SpuMapperProvider.class,method = "putMany")
    int putMany(@Param("ids") long[] ids);

    /**
     * 商品下架
     *
     * @param id
     * @return
     */
    @Update("update tb_spu set is_marketable=0 where id=#{id} and is_delete=0 and is_marketable=1 and status=1")
    int pullSpu(long id);

    /**
     * 批量下架商品
     *
     * @param ids
     */
    @UpdateProvider(type = SpuMapperProvider.class,method = "pullMany")
    int pullMany(@Param("ids") long[] ids);

    class SpuMapperProvider {
        public String putMany(long[] ids) {
            String s = Arrays.toString(ids);
            s = s.substring(1, s.length() - 1);
            return "update tb_spu set is_marketable=1 where id in(" + s
                    + ") and is_delete=0 and is_marketable=0 and status=1";
        }

        public String pullMany(long[] ids) {
            String s = Arrays.toString(ids);
            s = s.substring(1, s.length() - 1);
            return "update tb_spu set is_marketable=0 where id in(" + s
                    + ") and is_delete=0 and is_marketable=1 and status=1";
        }
    }

}
