package com.robod.mapper;

import com.robod.goods.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Robod
 * @date 2020/7/2 9:09
 */
@Repository("brandMapper")
public interface BrandMapper extends Mapper<Brand> {

    /**
     * 查询所有的品牌信息
     *
     * @return
     */
    @Select("select * from tb_brand")
    public List<Brand> findAll();

    /**
     * 根据id查询品牌信息
     *
     * @param id
     * @return
     */
    @Select("select * from tb_brand where id = #{id} limit 1")
    public Brand findById(Integer id);

    /**
     * 添加品牌
     *
     * @param brand
     */
    @Insert("insert into tb_brand values(#{brand.id},#{brand.name},#{brand.image},#{brand.letter},#{brand.seq})")
    public void add(@Param("brand") Brand brand);

    /**
     * 根据id修改品牌信息
     *
     * @param brand
     */
    @Insert("update tb_brand set name=#{brand.name},image=#{brand.image},letter=#{brand.letter},seq=#{brand.seq} where id=#{brand.id}")
    public void update(@Param("brand") Brand brand);

    /**
     * 根据id删除品牌
     *
     * @param id
     */
    @Delete("delete from tb_brand where id = #{id}")
    public void deleteBrand(Integer id);

    /**
     * 条件查询
     *
     * @param brand
     * @return
     */
    @SelectProvider(type = BrandMapperProvider.class, method = "findList")
    public List<Brand> findList(Brand brand);

    /**
     * 根据分类id查询对应的品牌集合
     * @param categoryId
     * @return
     * Dao层 BrandMapper.java
     */
    @Select("SELECT * FROM tb_brand WHERE id IN " +
            "(SELECT brand_id FROM tb_category_brand WHERE category_id=#{categoryId})")
    public List<Brand> findByCategory(int categoryId);

    class BrandMapperProvider {
        public String findList(Brand brand) {
            StringBuilder builder = new StringBuilder("select * from tb_brand where 0=0 ");
            if (brand != null) {
                if (!StringUtils.isEmpty(brand.getName())) {
                    builder.append(" and name like ").append("\"%").append(brand.getName()).append("%\" ");
                }
                if (!StringUtils.isEmpty(brand.getImage())) {
                    builder.append(" and image like ").append("\"%").append(brand.getImage()).append("%\" ");
                }
                if (!StringUtils.isEmpty(brand.getLetter())) {
                    builder.append(" and letter = ").append(" \"").append(brand.getLetter()).append("\" ");
                }
                if (brand.getSeq() != null) {
                    builder.append(" and seq = ").append(brand.getSeq());
                }
            }
            return builder.toString();
        }
    }

}
