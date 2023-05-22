package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.tedu.csmall.product.pojo.param.AttributeTemplateUpdateInfoParam;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateListItemVO;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateStandardVO;
import cn.tedu.csmall.product.pojo.vo.PageData;
import org.springframework.transaction.annotation.Transactional;

/**
 * 处理属性模板的业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Transactional
public interface IAttributeTemplateService {

    /**
     * 添加属性模板
     *
     * @param attributeTemplateAddNewParam 添加的属性模板对象
     */
    void addNew(AttributeTemplateAddNewParam attributeTemplateAddNewParam);

    /**
     * 根据ID删除商品属性模板
     *
     * @param id 被删除的商品属性模板的ID
     */
    void delete(Long id);

    /**
     * 修改属性模板基本资料
     *
     * @param id                               属性模板ID
     * @param attributeTemplateUpdateInfoParam 封装了新基本资料的对象
     */
    void updateInfoById(Long id, AttributeTemplateUpdateInfoParam attributeTemplateUpdateInfoParam);

    /**
     * 根据id获取属性模板的标准信息
     *
     * @param id 属性模板ID
     * @return 匹配的属性模板的标准信息，如果没有匹配的数据，将返回null
     */
    AttributeTemplateStandardVO getStandardById(Long id);

    /**
     * 查询属性模板列表，将使用默认的每页记录数
     *
     * @param pageNum 页码
     * @return 属性模板列表的集合
     */
    PageData<AttributeTemplateListItemVO> list(Integer pageNum);

    /**
     * 查询属性模板列表
     *
     * @param pageNum  页码
     * @param pageSize 每页记录数
     * @return 属性模板列表的集合
     */
    PageData<AttributeTemplateListItemVO> list(Integer pageNum, Integer pageSize);

}