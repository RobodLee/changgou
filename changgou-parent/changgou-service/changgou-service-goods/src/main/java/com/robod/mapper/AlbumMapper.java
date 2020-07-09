package com.robod.mapper;

import com.robod.goods.pojo.Album;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author Robod
 * @date 2020/7/7 8:59
 */
@Repository("albumMapper")
public interface AlbumMapper {

    /**
     * 查询全部列表
     *
     * @return
     */
    @Select("select * from tb_album")
//    @Results(id = "map",
//            value = @Result(property = "imageItems", column = "image_items")
//    )
    public List<Album> findAll();

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Select("select * from tb_album where id = #{id} limit 1")
//    @ResultMap("map")
    public Album findById(long id);

    /**
     * 增加
     *
     * @param album
     */
    @Insert("INSERT INTO tb_album" +
            " VALUES " +
            "(#{album.id},#{album.title},#{album.image},#{album.imageItems})")
    public void add(@Param("album") Album album);

    /**
     * 修改
     *
     * @param album
     */
    @Update("UPDATE tb_album" +
            " SET " +
            "id=#{album.id},title=#{album.title},image=#{album.image},image_items=#{album.imageItems}" +
            " WHERE id=#{album.id}")
    public void update(@Param("album") Album album);

    /***
     * 删除
     * @param id
     */
    @Delete("DELETE FROM tb_album WHERE id=#{id}")
    public void delete(long id);

    /***
     * 多条件搜索
     * @param album
     * @return
     */
    @SelectProvider(type = AlbumMapper.AlbumProvider.class, method = "findList")
    List<Album> findList(Album album);

    class AlbumProvider {
        public String findList(Album album) {
            StringBuilder builder = new StringBuilder("select * from tb_album where 0=0");
            // 相册名称
            if (album != null) {
                if (!StringUtils.isEmpty(album.getTitle())) {
                    builder.append(" and title like ").append("\"%").append(album.getTitle()).append("%\" ");
                }
                // 相册封面
                if (!StringUtils.isEmpty(album.getImage())) {
                    builder.append(" and image like ").append("\"%").append(album.getImage()).append("%\" ");
                }
                // 图片列表
                if (!StringUtils.isEmpty(album.getImageItems())) {
                    builder.append(" and image_items like ").append("\"%").append(album.getImageItems()).append("%\" ");
                }
            }

            System.out.println(builder.toString());
            return builder.toString();
        }
    }
}