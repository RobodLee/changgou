package com.robod.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.robod.goods.pojo.Album;
import com.robod.mapper.AlbumMapper;
import com.robod.service.intf.AlbumService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Robod
 * @date 2020/7/7 9:00
 */
@Service("albumService")
@Transactional(rollbackFor = Exception.class)
public class AlbumServiceImpl implements AlbumService {

    private final AlbumMapper albumMapper;

    public AlbumServiceImpl(AlbumMapper albumMapper) {
        this.albumMapper = albumMapper;
    }

    /**
     * 查询全部列表
     * @return
     */
    @Override
    public List<Album> findAll() {
        return albumMapper.findAll();
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @Override
    public Album findById(long id){
        return  albumMapper.findById(id);
    }

    /**
     * 增加
     * @param album
     */
    @Override
    public void add(Album album){
        albumMapper.add(album);
    }

    /**
     * 修改
     * @param album
     */
    @Override
    public void update(Album album){
        albumMapper.update(album);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(long id){
        albumMapper.delete(id);
    }


    /**
     * 条件查询
     * @param album
     * @return
     */
    @Override
    public List<Album> findList(Album album){
        return albumMapper.findList(album);
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Album> findPage(int page, int size){
        PageHelper.startPage(page,size);
        return (Page<Album>)albumMapper.findAll();
    }

    /**
     * 条件+分页查询
     * @param album 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public Page<Album> findPage(Album album, int page, int size){
        PageHelper.startPage(page,size);
        return (Page<Album>)albumMapper.findList(album);
    }

}
