package com.robod.mapper;

import com.robod.goods.pojo.Spec;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/****
 * @Author:admin
 * @Description:Spec的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("specMapper")
public interface SpecMapper extends Mapper<Spec> {

    /**
     * 根据模板id查询规格集合
     * @param templateId
     * @return
     */
    @Select("select * from tb_spec where template_id=#{templateId}")
    public List<Spec> findByTemplateId(int templateId);

}
