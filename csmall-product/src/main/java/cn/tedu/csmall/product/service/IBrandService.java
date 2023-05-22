package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.param.BrandAddNewParam;
import cn.tedu.csmall.product.pojo.param.BrandUpdateInfoParam;
import cn.tedu.csmall.product.pojo.vo.BrandListItemVO;
import cn.tedu.csmall.product.pojo.vo.BrandStandardVO;
import cn.tedu.csmall.product.pojo.vo.PageData;
import org.springframework.transaction.annotation.Transactional;

/**
 * 处理品牌的业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Transactional
public interface IBrandService {

    /**
     * 添加品牌
     *
     * @param brandAddNewParam 品牌数据
     */
    void addNew(BrandAddNewParam brandAddNewParam);

    /**
     * 根据ID删除品牌数据
     *
     * @param id 尝试删除的品牌的ID
     */
    void delete(Long id);

    /**
     * 根据品牌id，修改品牌详情
     *
     * @param id                   品牌ID
     * @param brandUpdateInfoParam 新的品牌数据
     */
    void updateInfoById(Long id, BrandUpdateInfoParam brandUpdateInfoParam);

    /**
     * 根据ID获取品牌详情
     *
     * @param id 品牌ID
     * @return 匹配的品牌数据详情，如果没有匹配的数据，则返回null
     */
    BrandStandardVO getStandardById(Long id);

    /**
     * 查询品牌列表，将使用默认的每页记录数
     *
     * @param pageNum 页码
     * @return 品牌列表，如果没有匹配的品牌，将返回长度为0的列表
     */
    PageData<BrandListItemVO> list(Integer pageNum);

    /**
     * 查询品牌列表
     *
     * @param pageNum  页码
     * @param pageSize 每页记录数
     * @return 品牌列表，如果没有匹配的品牌，将返回长度为0的列表
     */
    PageData<BrandListItemVO> list(Integer pageNum, Integer pageSize);

}
