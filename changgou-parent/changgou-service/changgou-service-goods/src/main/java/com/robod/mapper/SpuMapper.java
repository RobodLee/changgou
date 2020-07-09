package com.robod.mapper;
import com.robod.goods.pojo.Spu;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:Spu的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("spuMapper")
public interface SpuMapper extends Mapper<Spu> {

    /**
     * 根据id查询spu
     * @param id
     * @return
     */
    @Select("select * from tb_spu where id=#{id} limit 1")
    public Spu findById(long id);
}
