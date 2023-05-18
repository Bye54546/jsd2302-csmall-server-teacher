package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.param.AlbumAddNewParam;
import cn.tedu.csmall.product.pojo.param.AlbumUpdateInfoParam;
import cn.tedu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.tedu.csmall.product.pojo.vo.AlbumStandardVO;
import cn.tedu.csmall.product.pojo.vo.PageData;

public interface IAlbumService {

    void addNew(AlbumAddNewParam albumAddNewParam);

    void delete(Long id);

    void updateInfoById(Long id, AlbumUpdateInfoParam albumUpdateInfoParam);

    AlbumStandardVO getStandardById(Long id);

    PageData<AlbumListItemVO> list(Integer pageNum);

    PageData<AlbumListItemVO> list(Integer pageNum, Integer pageSize);

}
