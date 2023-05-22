package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.AttributeTemplate;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateListItemVO;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 处理属性模板数据的Mapper接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Repository
public interface AttributeTemplateMapper extends BaseMapper<AttributeTemplate> {

    /**
     * 根据id查询属性模板数据详情
     *
     * @param id 属性模板ID
     * @return 匹配的属性模板数据详情，如果没有匹配的数据，则返回null
     */
    AttributeTemplateStandardVO getStandardById(Long id);

    /**
     * 查询属性模板列表
     *
     * @return 属性模板列表的集合
     */
    List<AttributeTemplateListItemVO> list();

}