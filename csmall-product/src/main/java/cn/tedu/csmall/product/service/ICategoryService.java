package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.param.CategoryAddNewParam;
import cn.tedu.csmall.product.pojo.param.CategoryUpdateInfoParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * 处理类别业务的接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Transactional
public interface ICategoryService {

    /**
     * 添加类别
     *
     * @param categoryAddNewParam 新的类别数据
     */
    void addNew(CategoryAddNewParam categoryAddNewParam);

    /**
     * 根据ID删除类别
     *
     * @param id 尝试删除的类别数据的ID
     */
    void delete(Long id);

    /**
     * 修改类别数据
     *
     * @param id                      被修改的类别数据的ID
     * @param categoryUpdateInfoParam 类别的新数据
     */
    void updateInfoById(Long id, CategoryUpdateInfoParam categoryUpdateInfoParam);

}
