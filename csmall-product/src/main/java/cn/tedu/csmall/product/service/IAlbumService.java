package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.param.AlbumAddNewParam;
import cn.tedu.csmall.product.pojo.param.AlbumUpdateInfoParam;

public interface IAlbumService {

    void addNew(AlbumAddNewParam albumAddNewParam);

    void updateInfoById(Long id, AlbumUpdateInfoParam albumUpdateInfoParam);

}
