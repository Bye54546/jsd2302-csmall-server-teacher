package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Brand;
import cn.tedu.csmall.product.pojo.vo.BrandListItemVO;
import cn.tedu.csmall.product.pojo.vo.BrandStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 处理品牌数据的Mapper接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Repository
public interface BrandMapper extends BaseMapper<Brand> {

    /**
     * 根据id查询品牌数据详情
     *
     * @param id 品牌ID
     * @return 匹配的品牌数据详情，如果没有匹配的数据，则返回null
     */
    BrandStandardVO getStandardById(Long id);

    /**
     * 查询品牌数据列表
     *
     * @return 品牌数据列表
     */
    List<BrandListItemVO> list();

}
