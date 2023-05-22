package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.param.AlbumAddNewParam;
import cn.tedu.csmall.product.pojo.param.AlbumUpdateInfoParam;
import cn.tedu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.tedu.csmall.product.pojo.vo.AlbumStandardVO;
import cn.tedu.csmall.product.pojo.vo.PageData;
import org.springframework.transaction.annotation.Transactional;

/**
 * 处理相册数据的业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Transactional
public interface IAlbumService {

    /**
     * 添加相册
     *
     * @param albumAddNewParam 相册数据
     */
    void addNew(AlbumAddNewParam albumAddNewParam);

    /**
     * 根据ID删除相册
     *
     * @param id 尝试删除的相册数据的ID
     */
    void delete(Long id);

    /**
     * 修改相册数据
     *
     * @param id               被修改的相册数据的ID
     * @param albumUpdateInfoParam 相册的新数据
     */
    void updateInfoById(Long id, AlbumUpdateInfoParam albumUpdateInfoParam);

    /**
     * 根据ID查询相册数据详情
     *
     * @param id 相册ID
     * @return 匹配的相册数据详情，如果没有匹配的数据，则返回null
     */
    AlbumStandardVO getStandardById(Long id);

    /**
     * 查询相册数据列表，将使用默认的每页记录数
     *
     * @param pageNum 页码
     * @return 相册数据列表
     */
    PageData<AlbumListItemVO> list(Integer pageNum);

    /**
     * 查询相册数据列表
     *
     * @param pageNum  页码
     * @param pageSize 每页记录数
     * @return 相册数据列表
     */
    PageData<AlbumListItemVO> list(Integer pageNum, Integer pageSize);

}
