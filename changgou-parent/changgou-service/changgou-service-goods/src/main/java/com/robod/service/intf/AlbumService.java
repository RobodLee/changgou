package com.robod.service.intf;

import com.github.pagehelper.Page;
import com.robod.goods.pojo.Album;

import java.util.List;

/**
 * @author Robod
 * @date 2020/7/7 9:01
 */
public interface AlbumService {

    /***
     * 查询所有
     * @return
     */
    List<Album> findAll();

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    Album findById(long id);

    /***
     * 新增
     * @param album
     */
    void add(Album album);

    /***
     * 修改
     * @param album
     */
    void update(Album album);

    /***
     * 删除
     * @param id
     */
    void delete(long id);

    /***
     * 多条件搜索
     * @param album
     * @return
     */
    List<Album> findList(Album album);

    /***
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    Page<Album> findPage(int page, int size);

    /***
     * 多条件分页查询
     * @param album
     * @param page
     * @param size
     * @return
     */
    Page<Album> findPage(Album album, int page, int size);

}
