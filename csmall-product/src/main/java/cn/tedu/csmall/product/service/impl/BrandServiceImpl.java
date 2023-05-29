package cn.tedu.csmall.product.service.impl;

import cn.tedu.csmall.commons.ex.ServiceException;
import cn.tedu.csmall.commons.web.ServiceCode;
import cn.tedu.csmall.product.mapper.BrandMapper;
import cn.tedu.csmall.product.pojo.entity.Brand;
import cn.tedu.csmall.product.pojo.param.BrandAddNewParam;
import cn.tedu.csmall.product.pojo.param.BrandUpdateInfoParam;
import cn.tedu.csmall.product.pojo.vo.BrandListItemVO;
import cn.tedu.csmall.product.pojo.vo.BrandStandardVO;
import cn.tedu.csmall.product.pojo.vo.PageData;
import cn.tedu.csmall.product.service.IBrandService;
import cn.tedu.csmall.product.util.PageInfoToPageDataConverter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 处理品牌业务的实现类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Service
public class BrandServiceImpl implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;

    public BrandServiceImpl() {
        log.info("创建业务对象：BrandServiceImpl");
    }

    @Override
    public void addNew(BrandAddNewParam brandAddNewParam) {
        log.debug("开始处理【添加品牌】的业务，参数：{}", brandAddNewParam);
        // 应该保证此品牌的名称是唯一的
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", brandAddNewParam.getName());
        int countByName = brandMapper.selectCount(queryWrapper);
        log.debug("根据品牌名称统计匹配的品牌数量，结果：{}", countByName);
        if (countByName > 0) {
            String message = "添加品牌失败，品牌名称已经被占用！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // 创建品牌对象，用于插入到数据表
        Brand brand = new Brand();
        // 将参数DTO的各属性值复制到实体类对象中
        BeanUtils.copyProperties(brandAddNewParam, brand);
        // 补全数据
        brand.setSales(0);
        brand.setProductCount(0);
        brand.setCommentCount(0);
        brand.setPositiveCommentCount(0);
        brand.setGmtCreate(LocalDateTime.now());
        brand.setGmtModified(LocalDateTime.now());
        // 插入数据
        log.debug("即将向数据库中插入数据：{}", brand);
        int rows = brandMapper.insert(brand);
        if (rows != 1) {
            String message = "添加品牌失败，服务器忙，请稍后再尝试！";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【根据ID删除品牌】的业务，参数：{}", id);
        // 检查品牌是否存在，如果不存在，则抛出异常
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        int countById = brandMapper.selectCount(queryWrapper);
        log.debug("根据品牌ID统计匹配的品牌数量，结果：{}", countById);
        if (countById == 0) {
            String message = "删除品牌失败，品牌数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 暂未检查关联数据

        int rows = brandMapper.deleteById(id);
        if (rows != 1) {
            String message = "删除品牌失败，服务器忙，请稍后再试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
    }

    @Override
    public void updateInfoById(Long id, BrandUpdateInfoParam brandUpdateInfoParam) {
        log.debug("开始处理【修改品牌详情】的业务，参数ID：{}, 新数据：{}", id, brandUpdateInfoParam);
        // 检查品牌是否存在，如果不存在，则抛出异常
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        int countById = brandMapper.selectCount(queryWrapper);
        log.debug("根据品牌ID统计匹配的品牌数量，结果：{}", countById);
        if (countById == 0) {
            String message = "修改品牌详情失败，品牌数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 检查品牌名称是否被其它品牌占用，如果被占用，则抛出异常
        QueryWrapper<Brand> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("name", brandUpdateInfoParam.getName())
                .ne("id", id);
        int countByName = brandMapper.selectCount(queryWrapper2);
        log.debug("根据品牌名称统计匹配的品牌数量，结果：{}", countByName);
        if (countByName > 0) {
            String message = "修改品牌详情失败，品牌名称已经被占用！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // 执行修改
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandUpdateInfoParam, brand);
        brand.setId(id);
        int rows = brandMapper.updateById(brand);
        if (rows != 1) {
            String message = "修改品牌详情失败，服务器忙，请稍后再试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
        log.debug("将新的品牌数据更新入到数据库，完成！");
    }

    @Override
    public BrandStandardVO getStandardById(Long id) {
        log.debug("开始处理【根据ID查询品牌详情】的业务，参数：{}", id);
        BrandStandardVO queryResult = brandMapper.getStandardById(id);
        if (queryResult == null) {
            String message = "查询品牌详情失败，品牌数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        return queryResult;
    }

    @Override
    public PageData<BrandListItemVO> list(Integer pageNum) {
        Integer pageSize = 5;
        return list(pageNum, pageSize);
    }

    @Override
    public PageData<BrandListItemVO> list(Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询品牌列表】的业务，页码：{}，每页记录数：{}", pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<BrandListItemVO> list = brandMapper.list();
        PageInfo<BrandListItemVO> pageInfo = new PageInfo<>(list);
        PageData<BrandListItemVO> pageData = PageInfoToPageDataConverter.convert(pageInfo);
        log.debug("查询完成，即将返回：{}", pageData);
        return pageData;
    }

}
